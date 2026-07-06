package com.ssing.data.consumerlogin.repository.impl

import com.ssing.core.network.token.TokenAccessManager
import com.ssing.core.network.util.ApiResponseHandler
import com.ssing.data.consumerlogin.remote.datasource.api.ConsumerAuthDataSource
import com.ssing.data.consumerlogin.repository.api.ConsumerAuthRepository
import javax.inject.Inject

internal class ConsumerAuthRepositoryImpl @Inject constructor(
    private val apiResponseHandler: ApiResponseHandler,
    private val dataSource: ConsumerAuthDataSource,
    private val tokenAccessManager: TokenAccessManager,
) : ConsumerAuthRepository {

    override suspend fun postConsumerKakaoAuth(kakaoAccessToken: String): Result<Unit> =
        apiResponseHandler.safeApiCall {
            dataSource.postConsumerKakaoAuth(kakaoAccessToken)
        }.onSuccess { response ->
            tokenAccessManager.withLock {
                setAccessToken(response.accessToken)
                setRefreshToken(response.refreshToken)
            }
        }.map { Unit }
}