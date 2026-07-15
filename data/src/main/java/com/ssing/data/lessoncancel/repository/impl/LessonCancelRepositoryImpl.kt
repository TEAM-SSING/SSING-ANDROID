package com.ssing.data.lessoncancel.repository.impl

import com.ssing.core.network.util.ApiResponseHandler
import com.ssing.data.lessoncancel.remote.datasource.api.LessonCancelDataSource
import com.ssing.data.lessoncancel.repository.api.LessonCancelRepository
import javax.inject.Inject

internal class LessonCancelRepositoryImpl @Inject constructor(
    private val apiResponseHandler: ApiResponseHandler,
    private val dataSource: LessonCancelDataSource,
) : LessonCancelRepository {
    override suspend fun postLessonCancel(
        lessonId: Long,
        cancelReason: String,
        cancelReasonDetail: String?,
    ): Result<Unit> =
        apiResponseHandler.safeApiCall {
            dataSource.postLessonCancel(
                lessonId,
                cancelReason,
                cancelReasonDetail
            )
        }.map { }
}