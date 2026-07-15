package com.ssing.data.lesson.common.repository.api

import com.ssing.data.lesson.common.model.LessonStartConfirmationResult

interface LessonRepository {
    suspend fun lessonStart(lessonId: Long): Result<LessonStartConfirmationResult>
    suspend fun lessonCompleted(lessonId: Long): Result<Unit>
    suspend fun lessonCanceled(
        lessonId: Long,
        cancelReason: String,
        cancelReasonDetail: String?,
    ): Result<Unit>
}