package com.ssing.data.consumerlesson.remote.dto.response.matchingrequest

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ConsumerLessonMatchingRequest(
    @SerialName("matchingRequestId")
    val matchingRequestId: Long,
    @SerialName("representativeMemberId")
    val representativeMemberId: Long,
    @SerialName("representativeMemberName")
    val representativeMemberName: String,
    @SerialName("headcount")
    val headcount: Int,
    @SerialName("participants")
    val participants: List<Participant>,

    // confirmed
    @SerialName("startConfirmed")
    val startConfirmed: Boolean? = null,
)

@Serializable
data class Participant(
    @SerialName("participantId")
    val participantId: Long,
    @SerialName("gender")
    val gender: String,
    @SerialName("age")
    val age: Int,
)