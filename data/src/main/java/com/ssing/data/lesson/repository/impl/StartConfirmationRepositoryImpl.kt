package com.ssing.data.lesson.repository.impl

import com.ssing.core.network.exception.ApiException
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
            .recoverCatching { throwable ->
                throw mapToStartConfirmationException(throwable)
            }

    private fun mapToStartConfirmationException(throwable: Throwable): Throwable {
        if (throwable !is ApiException) return throwable

        val code = throwable.serverCode
        val message = throwable.message
        val requestId = throwable.requestId

        return when (throwable) {
            is ApiException.Unauthorized -> when (code) {
                "AUTH_INVALID_TOKEN" -> StartConfirmationException.AuthInvalidToken(code, message, requestId)
                "AUTH_TOKEN_EXPIRED" -> StartConfirmationException.AuthTokenExpired(code, message, requestId)
                else -> StartConfirmationException.Unauthenticated(code, message, requestId)
            }

            is ApiException.Forbidden ->
                StartConfirmationException.Forbidden(code, message, requestId)

            is ApiException.NotFound ->
                StartConfirmationException.LessonNotFound(code, message, requestId)

            is ApiException.Conflict -> when (code) {
                "LESSON_INVALID_STATE" -> StartConfirmationException.LessonInvalidState(code, message, requestId)
                else -> StartConfirmationException.LessonStartNotAllowed(code, message, requestId)
            }

            is ApiException.InternalServerError ->
                StartConfirmationException.InternalError(code, message, requestId)

            else -> throwable
        }
    }
}