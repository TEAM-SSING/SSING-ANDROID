package com.ssing.data.consumerlogin.repository.api

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.consumerlogin.remote.dto.ConsumerAuthResponse

interface ConsumerAuthRepository {
    suspend fun postConsumerKakaoAuth(
        kakaoAccessToken: String,
    ): BaseResponse<ConsumerAuthResponse>
}