package com.ssing.data.instructorlessondetail.remote.datasource.api

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.instructorlessondetail.remote.dto.request.InstructorLessonDetailRequest
import com.ssing.data.instructorlessondetail.remote.dto.response.InstructorLessonDetailResponse

internal interface InstructorLessonDetailDataSource {
    suspend fun instructorLessonDetail(
        lessonId: Int,
        request: InstructorLessonDetailRequest,
    ): BaseResponse<InstructorLessonDetailResponse>
}