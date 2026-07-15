package com.ssing.data.lesson.common.repository.api

interface LessonRepository {
    suspend fun lessonStart(
        lessonId: Long
    ): Result<Unit>

    suspend fun lessonCompleted(
        lessonId: Long
    ): Result<Unit>

    suspend fun lessonCanceled(
        lessonId: Long,
        cancelReason: String,
        cancelReasonDetail: String? = null,
    ): Result<Unit>
}