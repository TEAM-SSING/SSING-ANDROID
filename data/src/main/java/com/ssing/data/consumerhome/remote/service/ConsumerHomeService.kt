package com.ssing.data.consumerhome.remote.service

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.consumerhome.remote.dto.response.ConsumerHomeResponse
import retrofit2.http.GET

internal interface ConsumerHomeService {
    @GET("api/v1/consumer/home")
    suspend fun getConsumerHome(): BaseResponse<ConsumerHomeResponse>
}