package com.ssing.data.consumerhome.remote.datasource.impl

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.consumerhome.remote.datasource.api.ConsumerHomeRemoteDataSource
import com.ssing.data.consumerhome.remote.dto.response.ConsumerHomeResponse
import com.ssing.data.consumerhome.remote.service.ConsumerHomeService
import javax.inject.Inject

internal class ConsumerHomeRemoteDataSourceImpl @Inject constructor(
    private val service: ConsumerHomeService,
) : ConsumerHomeRemoteDataSource {

    override suspend fun getConsumerHome():
            BaseResponse<ConsumerHomeResponse> = service.getConsumerHome()
}
