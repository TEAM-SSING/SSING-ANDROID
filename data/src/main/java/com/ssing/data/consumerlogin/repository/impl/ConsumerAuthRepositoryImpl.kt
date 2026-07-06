package com.ssing.data.consumerlogin.repository.impl

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.consumerlogin.remote.datasource.api.ConsumerAuthDataSource
import com.ssing.data.consumerlogin.remote.dto.ConsumerAuthResponse
import com.ssing.data.consumerlogin.repository.api.ConsumerAuthRepository
import javax.inject.Inject

internal class ConsumerAuthRepositoryImpl @Inject constructor(
    private val dataSource: ConsumerAuthDataSource,
) : ConsumerAuthRepository {

    override suspend fun postConsumerKakaoAuth(
        kakaoAccessToken: String,
    ): BaseResponse<ConsumerAuthResponse> = dataSource.postConsumerKakaoAuth(kakaoAccessToken)
}