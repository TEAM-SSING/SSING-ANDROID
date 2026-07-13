package com.ssing.data.instructorlessondetail.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class InstructorLessonDetailResponse(
    @SerialName("lessonId")
    val lessonId: Int,
    @SerialName("lessonStatus")
    val lessonStatus: String,
    @SerialName("statusInfo")
    val statusInfo: StatusInfo? = null,
    @SerialName("lessonInfo")
    val lessonInfo: LessonInfo? = null,
    @SerialName("matchingRequests")
    val matchingRequests: List<MatchingRequest>,

)

@Serializable
internal data class StatusInfo(
    @SerialName("confirmationCount")
    val confirmedCount: Int,
    @SerialName("requiredCount")
    val requiredCount: Int,
    @SerialName("currentActorConfirmed")
    val currentActorConfirmed: Boolean,
    @SerialName("instructorConfirmed")
    val instructorConfirmed: Boolean,
)

@Serializable
internal data class LessonInfo(
    @SerialName("representativeConsumerNames")
    val representativeConsumerNames: List<String>,
    @SerialName("totalHeadcount")
    val totalHeadcount: Int,
    @SerialName("resort")
    val resort: Resort? = null,
    @SerialName("sport")
    val sport: String,
    @SerialName("lessonLevel")
    val lessonLevel: String,
    @SerialName("scheduledAt")
    val scheduledAt: String,
    @SerialName("scheduledDurationMinutes")
    val scheduledDurationMinutes: Int,
    @SerialName("totalLessonPrice")
    val totalLessonPrice: Int,

)

@Serializable
internal data class Resort(
    @SerialName("code")
    val code: String,
    @SerialName("displayName")
    val displayName: String,
)

@Serializable
internal data class MatchingRequest(
    @SerialName("matchingRequestId")
    val matchingRequestId: Int,
    @SerialName("representativeMemberId")
    val representativeMemberId: Int,
    @SerialName("representativeMemberName")
    val representativeMemberName: String,
    @SerialName("headcount")
    val headcount: Int,
    @SerialName("teamLessonPrice")
    val teamLessonPrice: Int,
    @SerialName("startConfirmed")
    val startConfirmed: Boolean,
    @SerialName("participants")
    val participants: List<Participants>,
)

@Serializable
internal data class Participants(
    @SerialName("participantId")
    val participantId: Int,
    @SerialName("gender")
    val gender: String,
    @SerialName("age")
    val age: Int,
)