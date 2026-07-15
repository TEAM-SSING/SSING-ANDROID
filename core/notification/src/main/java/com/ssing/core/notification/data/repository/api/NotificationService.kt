package com.ssing.core.notification.data.repository.api

import com.ssing.core.notification.data.remote.dto.FcmTokenRegisterRequest
import com.ssing.core.notification.data.remote.dto.FcmTokenUnregisterRequest
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT


interface NotificationService {

    @PUT("api/v1/fcm-tokens")
    suspend fun registerFcmToken(
        @Body request: FcmTokenRegisterRequest,
    )

    @POST("api/v1/fcm-tokens/unregister")
    suspend fun unregisterFcmToken(
        @Body request: FcmTokenUnregisterRequest,
    )
}
