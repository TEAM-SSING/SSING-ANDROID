package com.ssing.data.lesson.repository.impl

import com.ssing.core.network.extension.mapApiException
import com.ssing.core.network.util.ApiResponseHandler
import com.ssing.data.lesson.exception.StartConfirmationException
import com.ssing.data.lesson.remote.datasource.api.StartConfirmationDataSource
import com.ssing.data.lesson.remote.dto.request.StartConfirmationRequest
import com.ssing.data.lesson.repository.api.StartConfirmationRepository
import javax.inject.Inject

internal class StartConfirmationRepositoryImpl @Inject constructor(
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
            .mapApiException { mapStartConfirmationException(it.serverCode, it.message, it.requestId) }

    private fun mapStartConfirmationException(
        serverCode: String?,
        message: String?,
        requestId: String?,
    ) = when (serverCode) {
        "UNAUTHENTICATED" -> StartConfirmationException.Unauthenticated(serverCode, message, requestId)
        "AUTH_INVALID_TOKEN" -> StartConfirmationException.AuthInvalidToken(serverCode, message, requestId)
        "AUTH_TOKEN_EXPIRED" -> StartConfirmationException.AuthTokenExpired(serverCode, message, requestId)
        "FORBIDDEN" -> StartConfirmationException.Forbidden(serverCode, message, requestId)
        "LESSON_NOT_FOUND" -> StartConfirmationException.LessonNotFound(serverCode, message, requestId)
        "LESSON_START_NOT_ALLOWED" -> StartConfirmationException.LessonStartNotAllowed(serverCode, message, requestId)
        "LESSON_INVALID_STATE" -> StartConfirmationException.LessonInvalidState(serverCode, message, requestId)
        "INTERNAL_ERROR" -> StartConfirmationException.InternalError(serverCode, message, requestId)
        else -> StartConfirmationException.InternalError(serverCode, message, requestId)
    }
}