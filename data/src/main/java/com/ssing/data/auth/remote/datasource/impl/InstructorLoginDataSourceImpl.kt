package com.ssing.data.instructorlogin.remote.datasource.impl

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.instructorlogin.remote.datasource.api.InstructorLoginDataSource
import com.ssing.data.instructorlogin.remote.dto.request.KakaoLoginRequest
import com.ssing.data.instructorlogin.remote.dto.response.KakaoLoginResponse
import com.ssing.data.instructorlogin.remote.service.InstructorLoginService
import javax.inject.Inject

internal class InstructorLoginDataSourceImpl @Inject constructor(
    private val service: InstructorLoginService,
) : InstructorLoginDataSource {

    override suspend fun postKakaoLogin(kakaoAccessToken: String): BaseResponse<KakaoLoginResponse> =
        service.postKakaoLogin(KakaoLoginRequest(kakaoAccessToken))

}
