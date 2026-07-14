package com.ssing.data.lessoncancel.remote.service

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.lessoncancel.remote.dto.request.LessonCancelRequest
import com.ssing.data.lessoncancel.remote.dto.response.LessonCancelResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

internal interface LessonCancelService {
    @POST("/api/v1/lessons/{lessonId}/cancellation")
    suspend fun postLessonCancel(
        @Path("lessonId") lessonId: Long,
        @Body request: LessonCancelRequest
    ): BaseResponse<LessonCancelResponse>
}