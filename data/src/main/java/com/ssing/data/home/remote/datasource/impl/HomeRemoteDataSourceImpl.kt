package com.ssing.data.home.remote.datasource.impl

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.home.remote.datasource.api.HomeRemoteDataSource
import com.ssing.data.home.remote.dto.response.ConsumerHomeResponse
import com.ssing.data.home.remote.service.HomeService
import javax.inject.Inject

internal class HomeRemoteDataSourceImpl @Inject constructor(
    private val service: HomeService,
) : HomeRemoteDataSource {

    override suspend fun getConsumerHome():
            BaseResponse<ConsumerHomeResponse> = service.getConsumerHome()
}
