package com.ssing.data.lesson.remote.service

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.lesson.remote.dto.request.LessonRequest
import com.ssing.data.lesson.remote.dto.response.InstructorLessonDetailResponse
import com.ssing.data.lesson.remote.dto.response.LessonResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

internal interface LessonService {
    @POST("api/v1/lessons/{lessonId}/start-confirmation")
    suspend fun lessonStart(
        @Path("lessonId") lessonId: Long,
        @Body request: LessonRequest,
    ): BaseResponse<LessonResponse>

    @POST("api/v1/lessons/{lessonId}/completion")
    suspend fun lessonCompleted(
        @Path("lessonId") lessonId: Long,
    ): BaseResponse<LessonResponse>
}

@GET("api/v1/instructor/lessons/{lessonId}")
suspend fun getInstructorLessonDetail(
    @Path("lessonId") lessonId: Long,
): BaseResponse<InstructorLessonDetailResponse>
}