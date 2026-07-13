package com.ssing.data.instructorlessondetail.repository.impl

import com.ssing.core.network.extension.mapApiException
import com.ssing.core.network.util.ApiResponseHandler
import com.ssing.data.instructorlessondetail.exception.InstructorLessonDetailException
import com.ssing.data.instructorlessondetail.remote.datasource.api.InstructorLessonDetailDataSource
import com.ssing.data.instructorlessondetail.repository.api.InstructorLessonDetailRepository
import javax.inject.Inject

internal class InstructorLessonDetailRepositoryImpl @Inject constructor(
    private val apiResponseHandler: ApiResponseHandler,
    private val dataSource: InstructorLessonDetailDataSource,
) : InstructorLessonDetailRepository {
    override suspend fun instructorLessonDetail(lessonId: Int): Result<Unit> =
    apiResponseHandler.safeApiCall {
        dataSource.instructorLessonDetail(
            lessonId = lessonId,
        )
    }.map { }.mapApiException { mapInstructorLessonDetailException(it.serverCode, it.message, it.requestId) }

    private fun mapInstructorLessonDetailException(
        serverCode: String?,
        message: String?,
        requestId: String?,
    ) = when (serverCode) {
        "UNAUTHORIZED" -> InstructorLessonDetailException.Unauthorized(serverCode, message, requestId)
        "FORBIDDEN" -> InstructorLessonDetailException.Forbidden(serverCode, message, requestId)
        "LESSON_NOT_FOUND" -> InstructorLessonDetailException.LessonNotFound(serverCode, message, requestId)
        "LESSON_CONFLICT" -> InstructorLessonDetailException.LessonConflict(serverCode, message, requestId)
        "INTERNAL_ERROR" -> InstructorLessonDetailException.InternalError(serverCode, message, requestId)
        else -> InstructorLessonDetailException.InternalError(serverCode, message, requestId)
    }
}