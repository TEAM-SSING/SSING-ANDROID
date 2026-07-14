package com.ssing.data.lessoncancel.remote.datasource.api

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.lessoncancel.remote.dto.response.LessonCancelResponse

internal interface LessonCancelDataSource {
    suspend fun postLessonCancel(
        lessonId: Long,
        cancelReason: String,
        cancelReasonDetail: String?,
    ): BaseResponse<LessonCancelResponse>
}