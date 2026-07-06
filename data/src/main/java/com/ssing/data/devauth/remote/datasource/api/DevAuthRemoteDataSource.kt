package com.ssing.data.devauth.remote.datasource.api

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.devauth.remote.dto.response.PersonasResponse
import com.ssing.data.devauth.remote.dto.request.TokenRequest
import com.ssing.data.devauth.remote.dto.response.TokenResponse

interface DevAuthRemoteDataSource {
    suspend fun getPersonas(): BaseResponse<PersonasResponse>

    suspend fun postToken(request: TokenRequest): BaseResponse<TokenResponse>
}
