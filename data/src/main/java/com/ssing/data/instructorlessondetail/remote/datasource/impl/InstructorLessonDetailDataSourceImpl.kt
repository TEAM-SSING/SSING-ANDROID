package com.ssing.data.instructorlessondetail.remote.datasource.impl

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.instructorlessondetail.remote.datasource.api.InstructorLessonDetailDataSource
import com.ssing.data.instructorlessondetail.remote.dto.response.InstructorLessonDetailResponse
import com.ssing.data.instructorlessondetail.remote.service.InstructorLessonDetailService
import javax.inject.Inject

internal class InstructorLessonDetailDataSourceImpl @Inject constructor(
    private val service: InstructorLessonDetailService,
) : InstructorLessonDetailDataSource {
    override suspend fun instructorLessonDetail(
        lessonId: Int
    ): BaseResponse<InstructorLessonDetailResponse> =
        service.getInstructorLessonDetail(lessonId)
}