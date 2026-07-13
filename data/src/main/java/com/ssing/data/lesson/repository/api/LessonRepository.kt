package com.ssing.data.lesson.repository.api

import com.ssing.data.lesson.remote.dto.response.InstructorLessonDetailResponse

interface LessonRepository {
    suspend fun lessonStart(lessonId: Long): Result<Unit>
}

interface InstructorLessonDetailRepository {
    suspend fun instructorLessonDetail(lessonId: Long): Result<InstructorLessonDetailResponse>
}
