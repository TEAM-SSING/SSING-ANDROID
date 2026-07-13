package com.ssing.data.lesson.remote.dto.response

import kotlinx.serialization.Serializable

@Serializable
internal enum class LessonStatus {
    CONFIRMED,
    IN_PROGRESS,
    COMPLETED,
    CANCELED
}