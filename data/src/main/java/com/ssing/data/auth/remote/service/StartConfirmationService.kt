package com.ssing.data.auth.remote.service

import com.ssing.data.auth.remote.dto.request.StartConfirmationRequest
import retrofit2.http.Body
import retrofit2.http.POST

internal interface StartConfirmationService {
    @POST("api/v1/lessons/{lessonId}/start-confirmation")
    suspend fun startConfirmation(
        @Body request: StartConfirmationRequest,
    )
}