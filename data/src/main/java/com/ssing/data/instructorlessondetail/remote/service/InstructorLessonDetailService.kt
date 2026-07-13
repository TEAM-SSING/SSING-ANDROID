package com.ssing.data.instructorlessondetail.remote.service

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.instructorlessondetail.remote.dto.response.InstructorLessonDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path

internal interface InstructorLessonDetailService {

    @GET("api/v1/instructor/lessons/{lessonId}")
    suspend fun getInstructorLessonDetail(
        @Path("lessonId")
        lessonId: Long
    ): BaseResponse<InstructorLessonDetailResponse>
}