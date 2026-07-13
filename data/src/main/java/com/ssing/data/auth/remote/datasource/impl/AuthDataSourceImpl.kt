package com.ssing.data.auth.remote.datasource.impl

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.auth.remote.datasource.api.AuthDataSource
import com.ssing.data.auth.remote.dto.request.ConsumerKakaoAuthRequest
import com.ssing.data.auth.remote.dto.request.InstructorKakaoLoginRequest
import com.ssing.data.auth.remote.dto.request.LogoutRequest
import com.ssing.data.auth.remote.dto.response.ConsumerKakaoAuthResponse
import com.ssing.data.auth.remote.dto.response.InstructorKakaoLoginResponse
import com.ssing.data.auth.remote.service.AuthService
import javax.inject.Inject

internal class AuthDataSourceImpl @Inject constructor(
    private val service: AuthService,
) : AuthDataSource {

    override suspend fun postConsumerKakaoAuth(kakaoAccessToken: String): BaseResponse<ConsumerKakaoAuthResponse> =
        service.postConsumerKakaoAuth(ConsumerKakaoAuthRequest(kakaoAccessToken))

    override suspend fun postInstructorKakaoAuth(kakaoAccessToken: String): BaseResponse<InstructorKakaoLoginResponse> =
        service.postInstructorKakaoAuth(InstructorKakaoLoginRequest(kakaoAccessToken))

    override suspend fun logout(refreshToken: String) =
        service.postLogout(LogoutRequest(refreshToken))
}