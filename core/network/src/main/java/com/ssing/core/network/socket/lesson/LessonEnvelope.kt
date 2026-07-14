package com.ssing.core.network.socket.lesson

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LessonEnvelope<T>(
    @SerialName("eventId") val eventId: String,
    @SerialName("eventType") val eventType: String,
    @SerialName("occurredAt") val occurredAt: String,
    @SerialName("recipientRole") val recipientRole: String,
    @SerialName("lessonId") val lessonId: Long,
    @SerialName("lessonStatus") val lessonStatus: String,
    @SerialName("payload") val payload: T,
)
