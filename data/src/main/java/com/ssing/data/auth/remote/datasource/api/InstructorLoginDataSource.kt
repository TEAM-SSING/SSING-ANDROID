package com.ssing.data.auth.remote.datasource.api

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.auth.remote.dto.response.InstructorKakaoLoginResponse

interface InstructorLoginDataSource {
    suspend fun postKakaoLogin(kakaoAccessToken: String): BaseResponse<InstructorKakaoLoginResponse>
}
