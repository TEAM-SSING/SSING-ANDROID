package com.ssing.data.lesson.repository.api

interface StartConfirmationRepository {
    suspend fun confirmLessonStart(lessonId: Long): Result<Unit>
}