package com.ssing.data.consumerhome.remote.datasource.api

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.consumerhome.remote.dto.response.ConsumerHomeResponse

internal interface ConsumerHomeRemoteDataSource{
    suspend fun getConsumerHome(): BaseResponse<ConsumerHomeResponse>
}