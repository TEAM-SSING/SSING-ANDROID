package com.ssing.data.auth.repository.impl

import com.ssing.core.network.extension.mapApiException
import com.ssing.core.network.token.TokenAccessManager
import com.ssing.core.network.util.ApiResponseHandler
import com.ssing.core.network.util.suspendRunCatching
import com.ssing.data.auth.exception.LogoutException
import com.ssing.data.auth.remote.datasource.api.LogoutDataSource
import com.ssing.data.auth.repository.api.LogoutRepository
import timber.log.Timber
import javax.inject.Inject

class LogoutRepositoryImpl @Inject constructor(
    private val apiResponseHandler: ApiResponseHandler,
    private val dataSource: LogoutDataSource,
    private val tokenAccessManager: TokenAccessManager,
) : LogoutRepository {

    override suspend fun logout(): Result<Unit> {
        val refreshToken = tokenAccessManager.withLock { getRefreshToken() }

        val result =
            if (refreshToken == null) {
                Result.success(Unit)
            } else {
                apiResponseHandler.safeUnitApiCall {
                    dataSource.logout(refreshToken)
                }
            }

        suspendRunCatching {
            tokenAccessManager.withLock { clearTokens() }
        }.onFailure { Timber.e(it, "clearTokens 실패") }

        return result.mapApiException {
            when (it.serverCode) {
                "VALIDATION_FAILED" -> LogoutException.ValidationFailed(
                    it.serverCode,
                    it.message,
                    it.requestId,
                )

                "AUTH_INVALID_TOKEN" -> LogoutException.InvalidToken(
                    it.serverCode,
                    it.message,
                    it.requestId,
                )

                else -> it
            }
        }
    }
}
