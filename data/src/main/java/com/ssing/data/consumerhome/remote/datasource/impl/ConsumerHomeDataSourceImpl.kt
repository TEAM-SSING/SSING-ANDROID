package com.ssing.data.consumerhome.remote.datasource.impl

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.consumerhome.remote.datasource.api.ConsumerHomeDataSource
import com.ssing.data.consumerhome.remote.dto.response.ConsumerHomeResponse
import com.ssing.data.consumerhome.remote.service.ConsumerHomeService
import javax.inject.Inject

internal class ConsumerHomeDataSourceImpl @Inject constructor(
    private val service: ConsumerHomeService,
) : ConsumerHomeDataSource {
    override suspend fun getConsumerHome(): BaseResponse<ConsumerHomeResponse> = service.getConsumerHome()
}