package com.ssing.data.lesson.remote.datasource.api

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.lesson.remote.dto.request.StartConfirmationRequest
import com.ssing.data.lesson.remote.dto.response.StartConfirmationResponse

interface StartConfirmationDataSource {
    suspend fun startConfirmation(
        lessonId: Long,
        request: StartConfirmationRequest,):
            BaseResponse<StartConfirmationResponse>
}