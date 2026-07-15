package com.ssing.data.lesson.common.remote.datasource.api

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.lesson.common.remote.dto.response.LessonResponse

internal interface LessonDataSource {
    suspend fun lessonStart(
        lessonId: Long,
    ): BaseResponse<LessonResponse>

    suspend fun lessonCompleted(
        lessonId: Long,
    ): BaseResponse<LessonResponse>

    suspend fun lessonCanceled(
        lessonId: Long,
        cancelReason: String,
        cancelReasonDetail: String?,
    ): BaseResponse<LessonResponse>
}