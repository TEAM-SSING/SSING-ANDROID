package com.ssing.data.consumerlesson.remote.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConsumerLessonDetailRequest(
    @SerialName("lessonId")
    val lessonId: Long
)