package com.ssing.data.lessoncancel.repository.impl

import com.ssing.core.network.util.ApiResponseHandler
import com.ssing.data.lessoncancel.model.LessonCancelModel
import com.ssing.data.lessoncancel.remote.datasource.api.LessonCancelDataSource
import com.ssing.data.lessoncancel.remote.dto.response.LessonCancelResponse
import com.ssing.data.lessoncancel.repository.api.LessonCancelRepository
import javax.inject.Inject

internal class LessonCancelRepositoryImpl @Inject constructor(
    private val apiResponseHandler: ApiResponseHandler,
    private val dataSource: LessonCancelDataSource,
) : LessonCancelRepository {
    override suspend fun postLessonCancel(
        lessonId: Long,
        cancelReason: String,
        cancelReasonDetail: String
    ): Result<LessonCancelModel> =
        apiResponseHandler.safeApiCall {
            dataSource.postLessonCancel(
                lessonId,
                cancelReason,
                cancelReasonDetail
            )
        }.map { it.toModel() }

    private fun LessonCancelResponse.toModel(): LessonCancelModel = LessonCancelModel(
        lessonId = lessonId,
        lessonStatus = lessonStatus,
        canceledAt = canceledAt,
    )
}