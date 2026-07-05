package com.ssing.data.devauth.remote.service

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.devauth.remote.dto.PersonasResponse
import com.ssing.data.devauth.remote.dto.TokenRequest
import com.ssing.data.devauth.remote.dto.TokenResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface DevAuthService {
    @GET("dev/auth/personas")
    suspend fun getPersonas(): BaseResponse<PersonasResponse>

    @POST("dev/auth/token")
    suspend fun postToken(
        @Body request: TokenRequest,
    ): BaseResponse<TokenResponse>
}
