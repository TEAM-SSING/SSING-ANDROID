package com.ssing.data.repository.api

import com.ssing.data.remote.dto.NotificationRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Query


interface NotificationService {
    @POST("api/v1/fcm-tokens")
    suspend fun postNotificationToken(
        @Body request: NotificationRequest,
    )
    @DELETE("api/v1/fcm-tokens")
    suspend fun deleteNotificationToken(
        @Body request: NotificationRequest,
    )
}
