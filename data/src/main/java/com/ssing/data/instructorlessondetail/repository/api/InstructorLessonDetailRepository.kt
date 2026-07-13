package com.ssing.data.instructorlessondetail.repository.api

interface InstructorLessonDetailRepository {
    suspend fun instructorLessonDetail(lessonId: Long): Result<Unit>
}