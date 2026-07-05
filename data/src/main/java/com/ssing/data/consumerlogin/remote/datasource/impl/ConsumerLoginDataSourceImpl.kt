package com.ssing.data.consumerlogin.remote.datasource.impl

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.consumerlogin.remote.datasource.api.ConsumerLoginDataSource
import com.ssing.data.consumerlogin.remote.dto.ConsumerLoginRequest
import com.ssing.data.consumerlogin.remote.dto.ConsumerLoginResponse
import com.ssing.data.consumerlogin.remote.service.ConsumerLoginService
import javax.inject.Inject

internal class ConsumerLoginDataSourceImpl @Inject constructor(
    private val consumerLoginService: ConsumerLoginService,
) : ConsumerLoginDataSource {

    override suspend fun postConsumerKakaoLogin(kakaoAccessToken: String): BaseResponse<ConsumerLoginResponse> =
        consumerLoginService.postConsumerKakaoLogin(
            ConsumerLoginRequest(kakaoAccessToken),
        )
}