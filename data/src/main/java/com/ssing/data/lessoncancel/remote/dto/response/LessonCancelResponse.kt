package com.ssing.data.lessoncancel.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LessonCancelResponse(
    @SerialName("lessonId")
    val lessonId: Long,
    @SerialName("lessonStatus")
    val lessonStatus: String,
    @SerialName("canceledAt")
    val canceledAt: String,
)