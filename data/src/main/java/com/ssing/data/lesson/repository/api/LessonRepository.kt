package com.ssing.data.lesson.repository.api

interface LessonRepository {
    suspend fun lessonStart(lessonId: Long): Result<Unit>
}

interface InstructorLessonDetailRepository {
    suspend fun instructorLessonDetail(lessonId: Long): Result<Unit>
}
