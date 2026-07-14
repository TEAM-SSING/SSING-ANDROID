package com.ssing.data.lesson.repository.api

import com.ssing.data.lesson.model.InstructorLessonDetailRequestResult


interface LessonRepository {
    suspend fun lessonStart(lessonId: Long): Result<Unit>
}
