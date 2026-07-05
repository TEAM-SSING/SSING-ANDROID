package com.ssing.data.instructorlogin.repository.impl

import com.ssing.core.network.extension.mapApiException
import com.ssing.core.network.token.TokenAccessManager
import com.ssing.core.network.util.ApiResponseHandler
import com.ssing.data.instructorlogin.exception.InstructorLoginException
import com.ssing.data.instructorlogin.model.InstructorLoginResult
import com.ssing.data.instructorlogin.remote.datasource.api.InstructorLoginDataSource
import com.ssing.data.instructorlogin.remote.dto.response.KakaoLoginResponse
import com.ssing.data.instructorlogin.repository.api.InstructorLoginRepository
import javax.inject.Inject

class InstructorLoginRepositoryImpl @Inject constructor(
    private val apiResponseHandler: ApiResponseHandler,
    private val dataSource: InstructorLoginDataSource,
    private val tokenAccessManager: TokenAccessManager,
) : InstructorLoginRepository {

    override suspend fun loginWithKakao(kakaoAccessToken: String): Result<InstructorLoginResult> =
        apiResponseHandler.safeApiCall {
            dataSource.postKakaoLogin(kakaoAccessToken)
        }.onSuccess { response ->
            tokenAccessManager.withLock {
                setAccessToken(response.accessToken)
                setRefreshToken(response.refreshToken)
            }
        }.map { it.toInstructorLoginResult() }
            .mapApiException {
                when (it.serverCode) {
                    "VALIDATION_FAILED" -> InstructorLoginException.ValidationFailed(it.serverCode, it.message, it.requestId)
                    "AUTH_INVALID_KAKAO_TOKEN" -> InstructorLoginException.AuthInvalidKakaoToken(it.serverCode, it.message, it.requestId)
                    "EXTERNAL_SERVICE_UNAVAILABLE" -> InstructorLoginException.ExternalServiceUnavailable(it.serverCode, it.message, it.requestId)
                    else -> it
                }
            }

    private fun KakaoLoginResponse.toInstructorLoginResult() = InstructorLoginResult(
        id = this.member.id,
        nickname = this.member.nickname,
        role = this.member.role,
        memberStatus = this.member.memberStatus,
        instructorStatus = this.member.instructorStatus,
    )
}