package com.ssing.data.home.remote.service

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.home.remote.dto.response.ConsumerHomeResponse
import com.ssing.data.home.remote.dto.response.InstructorHomeResponse
import retrofit2.http.GET

internal interface HomeService {
    @GET("api/v1/consumer/home")
    suspend fun getConsumerHome(): BaseResponse<ConsumerHomeResponse>

    @GET("api/v1/instructor/home")
    suspend fun getInstructorHome(): BaseResponse<InstructorHomeResponse>
}
