package com.ssing.data.lessoncancel.repository.api

import com.ssing.data.lessoncancel.model.LessonCancelModel

interface LessonCancelRepository {
    suspend fun postLessonCancel(
        lessonId: Long,
        cancelReason: String,
        cancelReasonDetail: String,
    ): Result<Unit>
}