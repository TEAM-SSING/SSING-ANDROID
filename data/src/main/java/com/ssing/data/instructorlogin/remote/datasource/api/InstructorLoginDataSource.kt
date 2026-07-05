package com.ssing.data.instructorlogin.remote.datasource.api

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.instructorlogin.remote.dto.response.KakaoLoginResponse

interface InstructorLoginDataSource {
    suspend fun postKakaoLogin(kakaoAccessToken: String): BaseResponse<KakaoLoginResponse>
}
