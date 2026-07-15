package com.ssing.data.lesson.instructor.remote.service

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.lesson.instructor.remote.dto.response.InstructorLessonDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path

internal interface InstructorLessonService {
    @GET("api/v1/instructor/lessons/{lessonId}")
    suspend fun getInstructorLessonDetail(
        @Path("lessonId") lessonId: Long,
    ): BaseResponse<InstructorLessonDetailResponse>
}