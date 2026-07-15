package com.ssing.data.lesson.instructor.remote.datasource.api

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.lesson.instructor.remote.dto.response.InstructorLessonDetailResponse

internal interface InstructorLessonDataSource {
    suspend fun instructorLessonDetail(
        lessonId: Long,
    ): BaseResponse<InstructorLessonDetailResponse>
}