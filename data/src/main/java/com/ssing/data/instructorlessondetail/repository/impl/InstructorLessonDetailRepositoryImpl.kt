package com.ssing.data.instructorlessondetail.repository.impl

import com.ssing.core.network.util.ApiResponseHandler
import com.ssing.data.instructorlessondetail.remote.datasource.api.InstructorLessonDetailDataSource
import com.ssing.data.instructorlessondetail.repository.api.InstructorLessonDetailRepository
import javax.inject.Inject

internal class InstructorLessonDetailRepositoryImpl @Inject constructor(
    private val apiResponseHandler: ApiResponseHandler,
    private val dataSource: InstructorLessonDetailDataSource,
) : InstructorLessonDetailRepository {
    override suspend fun instructorLessonDetail(lessonId: Long): Result<Unit> =
        apiResponseHandler.safeApiCall {
            dataSource.instructorLessonDetail(
                lessonId = lessonId,
            )
        }.map { }
}