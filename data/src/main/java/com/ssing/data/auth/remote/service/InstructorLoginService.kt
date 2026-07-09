package com.ssing.data.auth.remote.service

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.auth.remote.dto.request.InstructorKakaoLoginRequest
import com.ssing.data.auth.remote.dto.response.InstructorKakaoLoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

internal interface InstructorLoginService {
    @POST("api/v1/instructor/auth/kakao")
    suspend fun postKakaoLogin(
        @Body request: InstructorKakaoLoginRequest,
    ): BaseResponse<InstructorKakaoLoginResponse>
}
