package com.ssing.data.home.remote.service

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.home.remote.dto.response.ConsumerHomeResponse
import retrofit2.http.GET

internal interface HomeService {
    @GET("api/v1/consumer/home")
    suspend fun getConsumerHome(): BaseResponse<ConsumerHomeResponse>
}