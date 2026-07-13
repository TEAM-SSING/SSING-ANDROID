package com.ssing.data.lesson.remote.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LessonRequest(
    @SerialName("lessonId")
    val lessonId: Long,
)