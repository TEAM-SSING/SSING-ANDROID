package com.ssing.data.auth.repository.impl

import com.ssing.core.network.extension.mapApiException
import com.ssing.core.network.token.TokenAccessManager
import com.ssing.core.network.util.ApiResponseHandler
import com.ssing.core.network.util.suspendRunCatching
import com.ssing.data.auth.exception.InstructorLoginException
import com.ssing.data.auth.model.InstructorLoginResult
import com.ssing.data.auth.remote.datasource.api.InstructorLoginDataSource
import com.ssing.data.auth.remote.dto.response.InstructorKakaoLoginResponse
import com.ssing.data.auth.repository.api.InstructorLoginRepository
import javax.inject.Inject

class InstructorLoginRepositoryImpl @Inject constructor(
    private val apiResponseHandler: ApiResponseHandler,
    private val dataSource: InstructorLoginDataSource,
    private val tokenAccessManager: TokenAccessManager,
) : InstructorLoginRepository {

    override suspend fun loginWithKakao(kakaoAccessToken: String): Result<Unit> =
        apiResponseHandler.safeApiCall {
            dataSource.postKakaoLogin(kakaoAccessToken)
        }.mapCatching { response ->
            try {
                tokenAccessManager.withLock {
                    setAccessToken(response.accessToken)
                    setRefreshToken(response.refreshToken)
                }
            } catch (e: Exception) {
                suspendRunCatching {
                    tokenAccessManager.withLock {
                        clearTokens()
                    }
                }
                throw e
            }
        }.mapApiException {
            when (it.serverCode) {
                "VALIDATION_FAILED" -> InstructorLoginException.ValidationFailed(
                    it.serverCode,
                    it.message,
                    it.requestId
                )

                "AUTH_INVALID_KAKAO_TOKEN" -> InstructorLoginException.AuthInvalidKakaoToken(
                    it.serverCode,
                    it.message,
                    it.requestId
                )

                "EXTERNAL_SERVICE_UNAVAILABLE" -> InstructorLoginException.ExternalServiceUnavailable(
                    it.serverCode,
                    it.message,
                    it.requestId
                )

                else -> it
            }
        }

    private fun InstructorKakaoLoginResponse.toInstructorLoginResult() = InstructorLoginResult(
        id = this.member.id,
        nickname = this.member.nickname,
        role = this.member.role,
        memberStatus = this.member.memberStatus,
        instructorStatus = this.member.instructorStatus,
    )
}