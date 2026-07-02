package com.ssing.core.network.service

import com.ssing.core.network.dto.request.TokenRefreshRequest
import com.ssing.core.network.dto.response.TokenRefreshResponse
import com.ssing.core.network.model.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ReissueService {
    @POST("api/v1/auth/refresh")
    suspend fun postRefresh(
        @Body request: TokenRefreshRequest,
    ): BaseResponse<TokenRefreshResponse>
}
