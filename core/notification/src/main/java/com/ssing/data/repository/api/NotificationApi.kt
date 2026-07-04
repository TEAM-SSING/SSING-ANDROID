package com.ssing.data.repository.api

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.remote.dto.NotificationRequestDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Query

interface NotificationApi {
    @POST("notifications/token")
    suspend fun postNotificationToken(
        @Body request: NotificationRequestDto,
    ): BaseResponse<Unit>

    @DELETE("notifications/token")
    suspend fun deleteNotificationToken(
        @Query("token") token: String,
    ): BaseResponse<Unit>
}
