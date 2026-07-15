package com.ssing.data.home.remote.datasource.api

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.home.remote.dto.response.ConsumerHomeResponse
import com.ssing.data.home.remote.dto.response.InstructorHomeResponse

internal interface HomeRemoteDataSource{
    suspend fun getConsumerHome(): BaseResponse<ConsumerHomeResponse>
    suspend fun getInstructorHome(): BaseResponse<InstructorHomeResponse>
}
