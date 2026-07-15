package com.ssing.data.lesson.common.repository.impl

import com.ssing.core.network.util.ApiResponseHandler
import com.ssing.data.lesson.common.remote.datasource.api.LessonDataSource
import com.ssing.data.lesson.common.repository.api.LessonRepository
import javax.inject.Inject

internal class LessonRepositoryImpl @Inject constructor(
    private val apiResponseHandler: ApiResponseHandler,
    private val dataSource: LessonDataSource,
) : LessonRepository {

    override suspend fun lessonStart(lessonId: Long): Result<Unit> =
        apiResponseHandler.safeApiCall {
            dataSource.lessonStart(
                lessonId = lessonId,
            )
        }.map { }

    override suspend fun lessonCompleted(lessonId: Long): Result<Unit> =
        apiResponseHandler.safeApiCall {
            dataSource.lessonCompleted(
                lessonId = lessonId,
            )
        }.map { }

    override suspend fun lessonCanceled(
        lessonId: Long,
        cancelReason: String,
        cancelReasonDetail: String?
    ): Result<Unit> =
        apiResponseHandler.safeApiCall {
            dataSource.lessonCanceled(
                lessonId = lessonId,
                cancelReason = cancelReason,
                cancelReasonDetail = cancelReasonDetail,
            )
        }.map { }

}