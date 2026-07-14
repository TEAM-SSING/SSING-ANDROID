package com.ssing.data.devauth.remote.datasource.impl

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.devauth.remote.datasource.api.DevAuthRemoteDataSource
import com.ssing.data.devauth.remote.dto.response.PersonasResponse
import com.ssing.data.devauth.remote.dto.request.TokenRequest
import com.ssing.data.devauth.remote.dto.response.TokenResponse
import com.ssing.data.devauth.remote.service.DevAuthService
import javax.inject.Inject

internal class DevAuthRemoteDataSourceImpl @Inject constructor(
    private val devAuthService: DevAuthService,
) : DevAuthRemoteDataSource {
    override suspend fun getPersonas(): BaseResponse<PersonasResponse> =
        devAuthService.getPersonas()

    override suspend fun postToken(request: TokenRequest): BaseResponse<TokenResponse> =
        devAuthService.postToken(request)
}
