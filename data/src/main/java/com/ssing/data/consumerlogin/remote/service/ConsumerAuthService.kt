package com.ssing.data.consumerlogin.remote.service

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.consumerlogin.remote.dto.ConsumerKakaoAuthRequest
import com.ssing.data.consumerlogin.remote.dto.ConsumerAuthResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ConsumerAuthService {

    @POST("api/v1/consumer/auth/kakao")
    suspend fun postConsumerKakaoAuth(
        @Body request: ConsumerKakaoAuthRequest,
    ): BaseResponse<ConsumerAuthResponse>
}