package com.ssing.data.lesson.repository.impl

import com.ssing.core.network.util.ApiResponseHandler
import com.ssing.data.lesson.remote.datasource.api.StartConfirmationDataSource
import com.ssing.data.lesson.remote.dto.request.StartConfirmationRequest
import com.ssing.data.lesson.repository.api.StartConfirmationRepository
import javax.inject.Inject

class StartConfirmationRepositoryImpl @Inject constructor(
    private val apiResponseHandler: ApiResponseHandler,
    private val dataSource: StartConfirmationDataSource,
) : StartConfirmationRepository {

    override suspend fun confirmLessonStart(lessonId: Long): Result<Unit> =
        apiResponseHandler.safeApiCall {
            dataSource.startConfirmation(
                lessonId = lessonId,
                request = StartConfirmationRequest(lessonId = lessonId),
            )
        }.map { }
}