package com.ssing.data.consumerlogin.repository.api

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.consumerlogin.remote.dto.ConsumerLoginResponse

interface ConsumerLoginRepository {
    suspend fun postConsumerKakaoLogin(
        kakaoAccessToken: String,
    ): BaseResponse<ConsumerLoginResponse>
}