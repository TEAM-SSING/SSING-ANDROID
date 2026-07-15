package com.ssing.data.consumerlesson.remote.dto.response.lessoninfo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConsumerLessonInfo(
    @SerialName("representativeConsumerNames")
    val representativeConsumerNames: List<String>,
    @SerialName("totalHeadcount")
    val totalHeadcount: Int,
    @SerialName("resort")
    val resort: Resort,
    @SerialName("sport")
    val sport: String,
    @SerialName("lessonLevel")
    val lessonLevel: String,
    @SerialName("myTeamLessonPrice")
    val myTeamLessonPrice: Int,

    // confirmed, in-progress
    @SerialName("scheduledAt")
    val scheduledAt: String? = null,
    @SerialName("scheduledDurationMinutes")
    val scheduledDurationMinutes: Int? = null,

    // completed
    @SerialName("actualStartedAt")
    val actualStartedAt: String? = null,
    @SerialName("actualEndedAt")
    val actualEndedAt: String? = null,
    @SerialName("actualDurationMinutes")
    val actualDurationMinutes: Int? = null,

    // completed, canceled
    @SerialName("lessonDurationMinutes")
    val lessonDurationMinutes: Int? = null,
)

@Serializable
data class Resort(
    @SerialName("code")
    val code: String,
    @SerialName("displayName")
    val displayName: String,
)