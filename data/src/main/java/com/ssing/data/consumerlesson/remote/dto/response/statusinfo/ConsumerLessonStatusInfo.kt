package com.ssing.data.consumerlesson.remote.dto.response.statusinfo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConsumerLessonStatusInfo(
    // confirmed
    @SerialName("confirmedCount")
    val confirmedCount: Int? = null,
    @SerialName("requiredCount")
    val requiredCount: Int? = null,
    @SerialName("currentActorConfirmed")
    val currentActorConfirmed: Boolean? = null,
    @SerialName("instructorConfirmed")
    val instructorConfirmed: Boolean? = null,

    // in-progress
    @SerialName("serverTime")
    val serverTime: String? = null,
    @SerialName("actualStartedAt")
    val actualStartedAt: String? = null,
    @SerialName("expectedEndedAt")
    val expectedEndedAt: String? = null,
    @SerialName("elapsedSeconds")
    val elapsedSeconds: Int? = null,
    @SerialName("remainingSeconds")
    val remainingSeconds: Int? = null,
)