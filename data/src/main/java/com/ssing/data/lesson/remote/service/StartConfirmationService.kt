package com.ssing.data.lesson.remote.service

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.lesson.remote.dto.request.StartConfirmationRequest
import com.ssing.data.lesson.remote.dto.response.StartConfirmationResponse
import retrofit2.http.Body
import retrofit2.http.POST

internal interface StartConfirmationService {
    @POST("api/v1/lessons/{lessonId}/start-confirmation")
    suspend fun startConfirmation(
        @Body request: StartConfirmationRequest,
    ): BaseResponse<StartConfirmationResponse>
}