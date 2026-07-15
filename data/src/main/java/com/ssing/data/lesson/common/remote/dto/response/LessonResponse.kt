package com.ssing.data.lesson.common.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LessonStartConfirmationResponse(
    @SerialName("lessonId") val lessonId: Long,
    @SerialName("lessonStatus") val lessonStatus: String,
    @SerialName("statusInfo") val statusInfo: StartConfirmationStatusInfoResponse? = null,
    @SerialName("startedAt") val startedAt: String? = null,
)

@Serializable
data class StartConfirmationStatusInfoResponse(
    @SerialName("confirmedCount") val confirmedCount: Int,
    @SerialName("requiredCount") val requiredCount: Int,
    @SerialName("currentActorConfirmed") val currentActorConfirmed: Boolean,
    @SerialName("instructorConfirmed") val instructorConfirmed: Boolean,
)

@Serializable
data class LessonCompletionResponse(
    @SerialName("lessonId") val lessonId: Long,
    @SerialName("lessonStatus") val lessonStatus: String,
    @SerialName("completedAt") val completedAt: String,
)

@Serializable
data class LessonCancellationResponse(
    @SerialName("lessonId") val lessonId: Long,
    @SerialName("lessonStatus") val lessonStatus: String,
    @SerialName("canceledAt") val canceledAt: String,
)