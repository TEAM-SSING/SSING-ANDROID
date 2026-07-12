package com.ssing.data.auth.remote.service

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.auth.remote.dto.request.ConsumerKakaoAuthRequest
import com.ssing.data.auth.remote.dto.response.ConsumerKakaoAuthResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ConsumerAuthService {

    @POST("api/v1/consumer/auth/kakao")
    suspend fun postConsumerKakaoAuth(
        @Body request: ConsumerKakaoAuthRequest,
    ): BaseResponse<ConsumerKakaoAuthResponse>
}