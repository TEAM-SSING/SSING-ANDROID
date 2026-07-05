package com.ssing.data.repository.api

import com.ssing.data.remote.dto.NotificationRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Query


interface NotificationService {
    @POST("notifications/token")
    suspend fun postNotificationToken(
        @Body request: NotificationRequest,
    )

    @DELETE("notifications/token")
    suspend fun deleteNotificationToken(
        @Query("token") token: String,
    )
}
