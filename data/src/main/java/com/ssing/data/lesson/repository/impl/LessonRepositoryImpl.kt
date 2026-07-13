package com.ssing.data.lesson.repository.impl

import com.ssing.core.network.extension.mapApiException
import com.ssing.core.network.util.ApiResponseHandler
import com.ssing.data.lesson.remote.datasource.api.LessonDataSource
import com.ssing.data.lesson.remote.dto.request.LessonRequest
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
            .mapApiException { mapLessonException(it.serverCode ?: "", it.message ?: "", it.requestId ?: "") }
}