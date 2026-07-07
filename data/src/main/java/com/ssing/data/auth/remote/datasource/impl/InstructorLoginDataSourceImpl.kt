package com.ssing.data.auth.remote.datasource.impl

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.auth.remote.datasource.api.InstructorLoginDataSource
import com.ssing.data.auth.remote.dto.request.InstructorKakaoLoginRequest
import com.ssing.data.auth.remote.dto.response.InstructorKakaoLoginResponse
import com.ssing.data.auth.remote.service.InstructorLoginService
import javax.inject.Inject

internal class InstructorLoginDataSourceImpl @Inject constructor(
    private val service: InstructorLoginService,
) : InstructorLoginDataSource {

    override suspend fun postKakaoLogin(kakaoAccessToken: String): BaseResponse<InstructorKakaoLoginResponse> =
        service.postKakaoLogin(InstructorKakaoLoginRequest(kakaoAccessToken))

}
