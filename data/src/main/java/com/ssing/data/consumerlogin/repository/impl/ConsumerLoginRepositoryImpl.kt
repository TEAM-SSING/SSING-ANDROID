package com.ssing.data.consumerlogin.repository.impl

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.consumerlogin.remote.datasource.api.ConsumerLoginDataSource
import com.ssing.data.consumerlogin.remote.dto.ConsumerLoginResponse
import com.ssing.data.consumerlogin.repository.api.ConsumerLoginRepository
import javax.inject.Inject

internal class ConsumerLoginRepositoryImpl @Inject constructor(
    private val dataSource: ConsumerLoginDataSource,
) : ConsumerLoginRepository {

    override suspend fun postConsumerKakaoLogin(
        kakaoAccessToken: String,
    ): BaseResponse<ConsumerLoginResponse> = dataSource.postConsumerKakaoLogin(kakaoAccessToken)
}