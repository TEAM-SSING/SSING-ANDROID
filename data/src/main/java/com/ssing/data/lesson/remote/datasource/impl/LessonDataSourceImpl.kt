package com.ssing.data.lesson.remote.datasource.impl

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.lesson.remote.datasource.api.LessonDataSource
import com.ssing.data.lesson.remote.dto.request.LessonRequest
import com.ssing.data.lesson.remote.dto.response.LessonResponse
import com.ssing.data.lesson.remote.service.LessonService
import javax.inject.Inject

internal class LessonDataSourceImpl @Inject constructor(
    private val service: LessonService,
) : LessonDataSource {

    override suspend fun lesson(
        lessonId: Long, request: LessonRequest
    ): BaseResponse<LessonResponse> =
        service.lesson(lessonId, request)
}