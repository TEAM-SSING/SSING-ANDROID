package com.ssing.data.lesson.remote.datasource.impl

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.lesson.remote.datasource.api.StartConfirmationDataSource
import com.ssing.data.lesson.remote.dto.request.StartConfirmationRequest
import com.ssing.data.lesson.remote.dto.response.StartConfirmationResponse
import com.ssing.data.lesson.remote.service.StartConfirmationService
import javax.inject.Inject

internal class StartConfirmationDataSourceImpl @Inject constructor(
    private val service: StartConfirmationService,
) : StartConfirmationDataSource {

    override suspend fun startConfirmation(
        lessonId: Long, request: StartConfirmationRequest
    ): BaseResponse<StartConfirmationResponse> =
        service.startConfirmation(lessonId, request)
}