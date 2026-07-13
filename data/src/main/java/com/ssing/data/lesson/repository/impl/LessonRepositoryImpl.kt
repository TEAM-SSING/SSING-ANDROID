package com.ssing.data.lesson.repository.impl

import com.ssing.core.network.util.ApiResponseHandler
import com.ssing.data.lesson.remote.datasource.api.InstructorLessonDetailDataSource
import com.ssing.data.lesson.remote.datasource.api.LessonDataSource
import com.ssing.data.lesson.remote.dto.request.LessonRequest
import com.ssing.data.lesson.remote.dto.response.InstructorLessonDetailResponse
import com.ssing.data.lesson.repository.api.InstructorLessonDetailRepository
import com.ssing.data.lesson.repository.api.LessonRepository
import javax.inject.Inject

internal class LessonRepositoryImpl @Inject constructor(
    private val apiResponseHandler: ApiResponseHandler,
    private val dataSource: LessonDataSource,
) : LessonRepository {

    override suspend fun lessonStart(lessonId: Long): Result<Unit> =
        apiResponseHandler.safeApiCall {
            dataSource.lesson(
                lessonId = lessonId,
                request = LessonRequest(lessonId = lessonId),
            )
        }.map { }
}

internal class InstructorLessonDetailRepositoryImpl @Inject constructor(
    private val apiResponseHandler: ApiResponseHandler,
    private val dataSource: InstructorLessonDetailDataSource,
) : InstructorLessonDetailRepository {
    override suspend fun instructorLessonDetail(lessonId: Long): Result<InstructorLessonDetailResponse> =
        apiResponseHandler.safeApiCall {
            dataSource.instructorLessonDetail(
                lessonId = lessonId,
            )
        }
}

