package com.ssing.data.lesson.common.remote.datasource.impl

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.lesson.common.remote.datasource.api.LessonDataSource
import com.ssing.data.lesson.common.remote.dto.response.LessonResponse
import com.ssing.data.lesson.common.remote.service.LessonService
import com.ssing.data.lessoncancel.remote.dto.request.LessonCancelRequest
import javax.inject.Inject

internal class LessonDataSourceImpl @Inject constructor(
    private val service: LessonService
) : LessonDataSource {
    override suspend fun lessonStart(lessonId: Long): BaseResponse<LessonResponse> =
        service.lessonStart(lessonId)

    override suspend fun lessonCompleted(lessonId: Long): BaseResponse<LessonResponse> =
        service.lessonCompleted(lessonId)

    override suspend fun lessonCanceled(
        lessonId: Long,
        cancelReason: String,
        cancelReasonDetail: String?
    ): BaseResponse<LessonResponse> = service.lessonCanceled(
        lessonId = lessonId,
        request = LessonCancelRequest(
            cancelReason,
            cancelReasonDetail,
        )
    )
}