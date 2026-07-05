package com.ssing.data.consumerlogin.remote.service

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.consumerlogin.remote.dto.ConsumerLoginRequest
import com.ssing.data.consumerlogin.remote.dto.ConsumerLoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ConsumerLoginService {

    @POST("api/v1/consumer/auth/kakao")
    suspend fun postConsumerKakaoLogin(
        @Body request: ConsumerLoginRequest,
    ): BaseResponse<ConsumerLoginResponse>
}