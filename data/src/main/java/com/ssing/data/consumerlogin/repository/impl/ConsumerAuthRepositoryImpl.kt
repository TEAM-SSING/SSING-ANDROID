package com.ssing.data.consumerlogin.repository.impl

import com.ssing.core.network.extension.mapApiException
import com.ssing.core.network.token.TokenAccessManager
import com.ssing.core.network.util.ApiResponseHandler
import com.ssing.core.network.util.suspendRunCatching
import com.ssing.data.consumerlogin.exception.ConsumerAuthException
import com.ssing.data.consumerlogin.remote.datasource.api.ConsumerAuthDataSource
import com.ssing.data.consumerlogin.repository.api.ConsumerAuthRepository
import timber.log.Timber
import javax.inject.Inject

internal class ConsumerAuthRepositoryImpl @Inject constructor(
    private val apiResponseHandler: ApiResponseHandler,
    private val dataSource: ConsumerAuthDataSource,
    private val tokenAccessManager: TokenAccessManager,
) : ConsumerAuthRepository {

    override suspend fun postConsumerKakaoAuth(kakaoAccessToken: String): Result<Unit> =
        apiResponseHandler.safeApiCall {
            dataSource.postConsumerKakaoAuth(kakaoAccessToken)
        }.mapCatching { response ->
            suspendRunCatching {
                tokenAccessManager.withLock {
                    setAccessToken(response.accessToken)
                    setRefreshToken(response.refreshToken)
                }
            }.onFailure { throwable ->
                suspendRunCatching {
                    tokenAccessManager.withLock {
                        clearTokens()
                    }
                }.onFailure { Timber.e(it, "clearTokens 실패") }
                throw throwable
            }
        }.map { }
            .mapApiException {
                when (it.serverCode) {
                    "VALIDATION_FAILED" -> ConsumerAuthException.ValidationFailed(
                        it.serverCode,
                        it.message,
                        it.requestId
                    )

                    "AUTH_INVALID_KAKAO_TOKEN" -> ConsumerAuthException.AuthInvalidKakaoToken(
                        it.serverCode,
                        it.message,
                        it.requestId
                    )

                    "EXTERNAL_SERVICE_UNAVAILABLE" -> ConsumerAuthException.ExternalServiceUnavailable(
                        it.serverCode,
                        it.message,
                        it.requestId
                    )

                    else -> it
                }
            }
}