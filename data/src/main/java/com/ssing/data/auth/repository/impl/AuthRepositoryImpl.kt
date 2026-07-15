package com.ssing.data.auth.repository.impl

import com.ssing.core.network.extension.mapApiException
import com.ssing.core.network.token.TokenAccessManager
import com.ssing.core.network.util.ApiResponseHandler
import com.ssing.core.network.util.suspendRunCatching
import com.ssing.core.notification.NotificationTokenProvider
import com.ssing.data.auth.exception.AuthException
import com.ssing.data.auth.remote.datasource.api.AuthDataSource
import com.ssing.data.auth.repository.api.AuthRepository
import timber.log.Timber
import javax.inject.Inject

internal class AuthRepositoryImpl @Inject constructor(
    private val apiResponseHandler: ApiResponseHandler,
    private val dataSource: AuthDataSource,
    private val tokenAccessManager: TokenAccessManager,
    private val notificationTokenProvider: NotificationTokenProvider,
) : AuthRepository {

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
                    tokenAccessManager.withLock { clearTokens() }
                }.onFailure { Timber.e(it, "clearTokens 실패") }
                throw throwable
            }
        }.map { }
            .mapApiException { mapLoginException(it.serverCode, it.message, it.requestId) }

    override suspend fun postInstructorKakaoAuth(kakaoAccessToken: String): Result<Unit> =
        apiResponseHandler.safeApiCall {
            dataSource.postInstructorKakaoAuth(kakaoAccessToken)
        }.mapCatching { response ->
            suspendRunCatching {
                tokenAccessManager.withLock {
                    setAccessToken(response.accessToken)
                    setRefreshToken(response.refreshToken)
                }
            }.onFailure { throwable ->
                suspendRunCatching {
                    tokenAccessManager.withLock { clearTokens() }
                }.onFailure { Timber.e(it, "clearTokens 실패") }
                throw throwable
            }
        }.map { }
            .onSuccess { notificationTokenProvider.registerCurrentToken() }
            .mapApiException { mapLoginException(it.serverCode, it.message, it.requestId) }

    override suspend fun postLogout(): Result<Unit> {
        // Access Token 제거 전에 먼저 FCM 토큰을 서버에서 해제한다.
        notificationTokenProvider.unregisterCurrentToken()

        val refreshToken = tokenAccessManager.withLock { getRefreshToken() }

        if (refreshToken != null) {
            apiResponseHandler.safeUnitApiCall {
                dataSource.logout(refreshToken)
            }.onFailure { Timber.e(it, "로그아웃 API 실패") }
        }

        return suspendRunCatching {
            tokenAccessManager.withLock { clearTokens() }
        }
    }

    private fun mapLoginException(
        serverCode: String?,
        message: String?,
        requestId: String?,
    ) = when (serverCode) {
        "VALIDATION_FAILED" -> AuthException.ValidationFailed(serverCode, message, requestId)
        "AUTH_INVALID_KAKAO_TOKEN" -> AuthException.InvalidKakaoToken(serverCode, message, requestId)
        "EXTERNAL_SERVICE_UNAVAILABLE" -> AuthException.ExternalServiceUnavailable(serverCode, message, requestId)
        else -> AuthException.ValidationFailed(serverCode, message, requestId)
    }
}