package com.ssing.data.auth.remote.service

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.auth.remote.dto.request.ConsumerKakaoAuthRequest
import com.ssing.data.auth.remote.dto.request.InstructorKakaoLoginRequest
import com.ssing.data.auth.remote.dto.request.LogoutRequest
import com.ssing.data.auth.remote.dto.response.ConsumerKakaoAuthResponse
import com.ssing.data.auth.remote.dto.response.InstructorKakaoLoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

internal interface AuthService {

    @POST("api/v1/consumer/auth/kakao")
    suspend fun postConsumerKakaoAuth(
        @Body request: ConsumerKakaoAuthRequest,
    ): BaseResponse<ConsumerKakaoAuthResponse>

    @POST("api/v1/instructor/auth/kakao")
    suspend fun postInstructorKakaoAuth(
        @Body request: InstructorKakaoLoginRequest,
    ): BaseResponse<InstructorKakaoLoginResponse>

    @POST("api/v1/auth/logout")
    suspend fun postLogout(
        @Body request: LogoutRequest,
    )
}

