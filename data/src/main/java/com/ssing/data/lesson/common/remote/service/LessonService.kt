package com.ssing.data.lesson.common.remote.service

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.lesson.common.remote.dto.request.LessonCancelRequest
import com.ssing.data.lesson.common.remote.dto.response.LessonCancellationResponse
import com.ssing.data.lesson.common.remote.dto.response.LessonCompletionResponse
import com.ssing.data.lesson.common.remote.dto.response.LessonStartConfirmationResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

internal interface LessonService {
    @POST("api/v1/lessons/{lessonId}/start-confirmation")
    suspend fun lessonStart(
        @Path("lessonId") lessonId: Long,
    ): BaseResponse<LessonStartConfirmationResponse>

    @POST("api/v1/lessons/{lessonId}/completion")
    suspend fun lessonCompleted(
        @Path("lessonId") lessonId: Long,
    ): BaseResponse<LessonCompletionResponse>

    @POST("api/v1/lessons/{lessonId}/cancellation")
    suspend fun lessonCanceled(
        @Path("lessonId") lessonId: Long,
        @Body request: LessonCancelRequest
    ): BaseResponse<LessonCancellationResponse>
}