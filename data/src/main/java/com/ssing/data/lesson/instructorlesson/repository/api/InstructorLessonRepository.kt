package com.ssing.data.lesson.instructorlesson.repository.api

import com.ssing.data.lesson.model.InstructorLessonDetailRequestResult

interface InstructorLessonRepository {
    suspend fun instructorLessonDetail(lessonId: Long): Result<InstructorLessonDetailRequestResult>
}