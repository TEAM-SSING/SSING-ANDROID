package com.ssing.data.lessoncancel.repository.api

interface LessonCancelRepository {
    suspend fun postLessonCancel(
        lessonId: Long,
        cancelReason: String,
        cancelReasonDetail: String? = null,
    ): Result<Unit>
}