package com.ssing.data.auth.remote.datasource.api

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.auth.remote.dto.response.ConsumerKakaoAuthResponse
import com.ssing.data.auth.remote.dto.response.InstructorKakaoLoginResponse

internal interface AuthDataSource {
    suspend fun postConsumerKakaoAuth(kakaoAccessToken: String): BaseResponse<ConsumerKakaoAuthResponse>
    suspend fun postInstructorKakaoAuth(kakaoAccessToken: String): BaseResponse<InstructorKakaoLoginResponse>
    suspend fun logout(refreshToken: String)
}