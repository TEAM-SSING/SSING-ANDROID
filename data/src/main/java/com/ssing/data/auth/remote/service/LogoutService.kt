package com.ssing.data.auth.remote.service

import com.ssing.data.auth.remote.dto.request.LogoutRequest
import retrofit2.http.Body
import retrofit2.http.POST

internal interface LogoutService {
    @POST("api/v1/auth/logout")
    suspend fun logout(
        @Body request: LogoutRequest,
    )
}
