package com.ssing.data.consumermatching.remote.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConsumerMatchingConditionRequest(
    @SerialName("resort") val resort: String,
    @SerialName("sport") val sport: String,
    @SerialName("lessonLevel") val lessonLevel: String,
    @SerialName("requestedDurationMinutes") val requestedDurationMinutes: List<Int>,
    @SerialName("participants") val consumerMatchingParticipantRequests: List<ConsumerMatchingParticipantRequest>,
    @SerialName("equipmentReady") val equipmentReady: Boolean,
)

@Serializable
data class ConsumerMatchingParticipantRequest(
    @SerialName("age") val age: Int,
    @SerialName("gender") val gender: String,
)
