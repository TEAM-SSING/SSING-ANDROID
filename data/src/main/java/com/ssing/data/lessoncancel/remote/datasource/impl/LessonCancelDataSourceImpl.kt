package com.ssing.data.lessoncancel.remote.datasource.impl

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.lessoncancel.remote.datasource.api.LessonCancelDataSource
import com.ssing.data.lessoncancel.remote.dto.request.LessonCancelRequest
import com.ssing.data.lessoncancel.remote.dto.response.LessonCancelResponse
import com.ssing.data.lessoncancel.remote.service.LessonCancelService
import javax.inject.Inject

internal class LessonCancelDataSourceImpl @Inject constructor(
    private val service: LessonCancelService,
) : LessonCancelDataSource {
    override suspend fun postLessonCancel(
        lessonId: Long,
        cancelReason: String,
        cancelReasonDetail: String?
    ): BaseResponse<LessonCancelResponse> = service.postLessonCancel(
        lessonId = lessonId,
        request = LessonCancelRequest(
            cancelReason,
            cancelReasonDetail,
        )
    )
}