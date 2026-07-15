package com.ssing.data.lesson.common.remote.datasource.impl

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.lesson.common.remote.datasource.api.LessonDataSource
import com.ssing.data.lesson.common.remote.dto.request.LessonCancelRequest
import com.ssing.data.lesson.common.remote.dto.response.LessonCancellationResponse
import com.ssing.data.lesson.common.remote.dto.response.LessonCompletionResponse
import com.ssing.data.lesson.common.remote.dto.response.LessonStartConfirmationResponse
import com.ssing.data.lesson.common.remote.service.LessonService
import javax.inject.Inject

internal class LessonDataSourceImpl @Inject constructor(
    private val service: LessonService,
) : LessonDataSource {
    override suspend fun lessonStart(lessonId: Long): BaseResponse<LessonStartConfirmationResponse> =
        service.lessonStart(lessonId)

    override suspend fun lessonCompleted(lessonId: Long): BaseResponse<LessonCompletionResponse> =
        service.lessonCompleted(lessonId)

    override suspend fun lessonCanceled(
        lessonId: Long,
        cancelReason: String,
        cancelReasonDetail: String?,
    ): BaseResponse<LessonCancellationResponse> = service.lessonCanceled(
        lessonId = lessonId,
        request = LessonCancelRequest(
            cancelReason,
            cancelReasonDetail,
        ),
    )
}