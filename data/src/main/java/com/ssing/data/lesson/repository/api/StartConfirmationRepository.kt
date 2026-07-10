package com.ssing.data.lesson.repository.api

import com.ssing.data.lesson.remote.dto.request.StartConfirmationRequest

interface StartConfirmationRepository {
    suspend fun confirmLessonStart(lessonId: Long): Result<Unit>
}