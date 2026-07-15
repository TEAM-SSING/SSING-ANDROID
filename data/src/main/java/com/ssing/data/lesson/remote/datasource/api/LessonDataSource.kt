package com.ssing.data.lesson.remote.datasource.api

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.lesson.remote.dto.request.LessonRequest
import com.ssing.data.lesson.remote.dto.response.InstructorLessonDetailResponse
import com.ssing.data.lesson.remote.dto.response.LessonResponse

internal interface LessonDataSource {
    suspend fun lessonStart(
        lessonId: Long,
        request: LessonRequest,
    ):
            BaseResponse<LessonResponse>

    suspend fun instructorLessonDetail(
        lessonId: Long,
    ): BaseResponse<InstructorLessonDetailResponse>

    suspend fun lessonCompleted(
        lessonId: Long,
    ): BaseResponse<LessonResponse>
}
