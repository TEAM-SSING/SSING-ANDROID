package com.ssing.data.consumerlogin.remote.datasource.api

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.consumerlogin.remote.dto.response.ConsumerAuthResponse

interface ConsumerAuthDataSource {
    suspend fun postConsumerKakaoAuth(
        kakaoAccessToken: String,
    ): BaseResponse<ConsumerAuthResponse>
}