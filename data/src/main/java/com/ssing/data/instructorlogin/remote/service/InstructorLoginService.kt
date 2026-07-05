package com.ssing.data.instructorlogin.remote.service

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.instructorlogin.remote.dto.request.KakaoLoginRequest
import com.ssing.data.instructorlogin.remote.dto.response.KakaoLoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

internal interface InstructorLoginService {
    @POST("api/v1/instructor/auth/kakao")
    suspend fun postKakaoLogin(
        @Body request: KakaoLoginRequest,
    ): BaseResponse<KakaoLoginResponse>
}
