package com.ssing.data.lesson.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StartConfirmationResponse(
    @SerialName("lessonId")
    val lessonId: Long,
    @SerialName("lessonStatus")
    val lessonStatus: String,
    @SerialName("currentActorConfirmed")
    val currentActorConfirmed: Boolean,
    @SerialName("confirmedCount")
    val confirmedCount: Int,
    @SerialName("requiredCount")
    val requiredCount: Int,
    @SerialName("startedAt")
    val startedAt: String,
)
