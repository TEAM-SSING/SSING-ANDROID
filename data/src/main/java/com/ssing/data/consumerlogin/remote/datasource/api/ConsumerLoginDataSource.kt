package com.ssing.data.consumerlogin.remote.datasource.api

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.consumerlogin.remote.dto.ConsumerLoginResponse

interface ConsumerLoginDataSource {
    suspend fun postConsumerKakaoLogin(
        kakaoAccessToken: String,
    ): BaseResponse<ConsumerLoginResponse>
}