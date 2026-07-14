package com.ssing.data.lesson.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class InstructorLessonEndResponse(
    @SerialName("lessonId") val lessonId: Long,
    @SerialName("lessonStatus") val lessonStatus: String,
    @SerialName("completedAt") val completedAt: String,
)