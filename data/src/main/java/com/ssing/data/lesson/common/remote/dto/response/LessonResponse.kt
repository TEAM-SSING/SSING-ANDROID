package com.ssing.data.lesson.common.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.io.Serial

@Serializable
internal data class LessonResponse(
    @SerialName("lessonId") val lessonId: Long,
    @SerialName("lessonStatus") val lessonStatus: String,
    // start
    @SerialName("statusInfo") val statusInfo: StatusInfo? = null,
    @SerialName("startedAt") val startedAt: String? = null,
    // completed
    @SerialName("completedAt") val completedAt: String? = null,
    // canceled
    @SerialName("canceledAt") val canceledAt: String? = null,
) {
    @Serializable
    data class StatusInfo(
        @SerialName("confirmedCount") val confirmedCount: Int,
        @SerialName("requiredCount") val requiredCount: Int,
        @SerialName("currentActorConfirmed") val currentActorConfirmed: Boolean,
        @SerialName("instructorConfirmed") val instructorConfirmed: Boolean,
    )
}