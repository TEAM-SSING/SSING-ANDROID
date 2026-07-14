package com.ssing.data.lesson.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class InstructorLessonDetailCompletedResponse(
    @SerialName("lessonId") val lessonId: Long,
    @SerialName("lessonStatus") val lessonStatus: LessonStatus,
    @SerialName("lessonInfo") val lessonInfo: LessonInfo,
    @SerialName("matchingRequests") val matchingRequests: List<MatchingRequest>,
) : InstructorLessonDetailResponse {
    @Serializable
    internal data class LessonInfo(
        @SerialName("representativeConsumerNames") val representativeConsumerNames: List<String>,
        @SerialName("totalHeadcount") val totalHeadcount: Int,
        @SerialName("resort") val resort: Resort,
        @SerialName("sport") val sport: String,
        @SerialName("lessonLevel") val lessonLevel: String,
        @SerialName("lessonDurationMinutes") val lessonDurationMinutes: Int,
        @SerialName("actualStartedAt") val actualStartedAt: String,
        @SerialName("actualEndedAt") val actualEndedAt: String,
        @SerialName("actualDurationMinutes") val actualDurationMinutes: Int,
        @SerialName("totalLessonPrice") val totalLessonPrice: Int,
    )

    @Serializable
    internal data class Resort(
        @SerialName("code") val code: String,
        @SerialName("displayName") val displayName: String,
    )

    @Serializable
    internal data class MatchingRequest(
        @SerialName("matchingRequestId") val matchingRequestId: Long,
        @SerialName("representativeMemberId") val representativeMemberId: Long,
        @SerialName("representativeMemberName") val representativeMemberName: String,
        @SerialName("headcount") val headcount: Int,
        @SerialName("teamLessonPrice") val teamLessonPrice: Int,
        @SerialName("participants") val participants: List<Participant>,
    )

    @Serializable
    internal data class Participant(
        @SerialName("participantId") val participantId: Long,
        @SerialName("gender") val gender: String,
        @SerialName("age") val age: Int,
    )
}