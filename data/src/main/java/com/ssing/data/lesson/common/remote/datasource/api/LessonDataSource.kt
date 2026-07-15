package com.ssing.data.lesson.common.remote.datasource.api

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.lesson.common.remote.dto.response.LessonCancellationResponse
import com.ssing.data.lesson.common.remote.dto.response.LessonCompletionResponse
import com.ssing.data.lesson.common.remote.dto.response.LessonStartConfirmationResponse

internal interface LessonDataSource {
    suspend fun lessonStart(lessonId: Long): BaseResponse<LessonStartConfirmationResponse>
    suspend fun lessonCompleted(lessonId: Long): BaseResponse<LessonCompletionResponse>
    suspend fun lessonCanceled(
        lessonId: Long,
        cancelReason: String,
        cancelReasonDetail: String?,
    ): BaseResponse<LessonCancellationResponse>
}