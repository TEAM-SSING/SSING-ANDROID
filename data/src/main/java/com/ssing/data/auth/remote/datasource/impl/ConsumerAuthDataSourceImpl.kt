package com.ssing.data.auth.remote.datasource.impl

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.auth.remote.datasource.api.ConsumerAuthDataSource
import com.ssing.data.auth.remote.dto.request.ConsumerKakaoAuthRequest
import com.ssing.data.auth.remote.dto.response.ConsumerAuthResponse
import com.ssing.data.auth.remote.service.ConsumerAuthService
import javax.inject.Inject

class ConsumerAuthDataSourceImpl @Inject constructor(
    private val consumerLoginService: ConsumerAuthService,
) : ConsumerAuthDataSource {

    override suspend fun postConsumerKakaoAuth(kakaoAccessToken: String): BaseResponse<ConsumerAuthResponse> =
        consumerLoginService.postConsumerKakaoAuth(
            ConsumerKakaoAuthRequest(kakaoAccessToken),
        )
}