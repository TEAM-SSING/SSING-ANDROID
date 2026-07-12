package com.ssing.data.auth.remote.datasource.api

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.auth.remote.dto.response.ConsumerKakaoAuthResponse

interface ConsumerAuthDataSource {
    suspend fun postConsumerKakaoAuth(kakaoAccessToken: String): BaseResponse<ConsumerKakaoAuthResponse>
}