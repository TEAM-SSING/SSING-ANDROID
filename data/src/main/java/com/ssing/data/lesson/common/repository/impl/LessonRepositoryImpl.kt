package com.ssing.data.lesson.common.repository.impl

import com.ssing.core.network.util.ApiResponseHandler
import com.ssing.data.lesson.common.model.LessonStartConfirmationResult
import com.ssing.data.lesson.common.remote.datasource.api.LessonDataSource
import com.ssing.data.lesson.common.remote.dto.response.LessonStartConfirmationResponse
import com.ssing.data.lesson.common.repository.api.LessonRepository
import javax.inject.Inject

internal class LessonRepositoryImpl @Inject constructor(
    private val apiResponseHandler: ApiResponseHandler,
    private val dataSource: LessonDataSource,
) : LessonRepository {

    override suspend fun lessonStart(lessonId: Long): Result<LessonStartConfirmationResult> =
        apiResponseHandler.safeApiCall {
            dataSource.lessonStart(lessonId)
        }.map { it.toModel() }

    override suspend fun lessonCompleted(lessonId: Long): Result<Unit> =
        apiResponseHandler.safeApiCall {
            dataSource.lessonCompleted(lessonId)
        }.map { }

    override suspend fun lessonCanceled(
        lessonId: Long,
        cancelReason: String,
        cancelReasonDetail: String?,
    ): Result<Unit> =
        apiResponseHandler.safeApiCall {
            dataSource.lessonCanceled(lessonId, cancelReason, cancelReasonDetail)
        }.map { }

    private fun LessonStartConfirmationResponse.toModel(): LessonStartConfirmationResult =
        when {
            startedAt != null -> LessonStartConfirmationResult.Started(
                lessonId = lessonId,
                startedAt = startedAt,
            )

            statusInfo != null -> LessonStartConfirmationResult.Pending(
                lessonId = lessonId,
                confirmedCount = statusInfo.confirmedCount,
                requiredCount = statusInfo.requiredCount,
                currentActorConfirmed = statusInfo.currentActorConfirmed,
                instructorConfirmed = statusInfo.instructorConfirmed,
            )

            else -> error("Unknown lessonStart : $this")
        }
}