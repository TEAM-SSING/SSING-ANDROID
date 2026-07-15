package com.ssing.data.lesson.instructor.remote.datasource.impl

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.lesson.instructor.remote.datasource.api.InstructorLessonDataSource
import com.ssing.data.lesson.instructor.remote.dto.response.InstructorLessonDetailResponse
import com.ssing.data.lesson.instructor.remote.service.InstructorLessonService
import javax.inject.Inject

internal class InstructorLessonDataSourceImpl @Inject constructor(
    private val service: InstructorLessonService,
) : InstructorLessonDataSource {
    override suspend fun instructorLessonDetail(lessonId: Long): BaseResponse<InstructorLessonDetailResponse> =
        service.getInstructorLessonDetail(lessonId)
}