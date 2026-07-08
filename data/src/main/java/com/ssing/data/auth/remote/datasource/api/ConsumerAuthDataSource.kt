package com.ssing.data.auth.remote.datasource.api

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.auth.remote.dto.response.ConsumerAuthResponse

interface ConsumerAuthDataSource {
    suspend fun postConsumerKakaoAuth(
        kakaoAccessToken: String,
    ): BaseResponse<ConsumerAuthResponse>
}