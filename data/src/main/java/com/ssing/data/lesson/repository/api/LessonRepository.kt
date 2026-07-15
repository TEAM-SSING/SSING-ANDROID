package com.ssing.data.lesson.repository.api


interface LessonRepository {
    suspend fun lessonStart(lessonId: Long): Result<Unit>
}
