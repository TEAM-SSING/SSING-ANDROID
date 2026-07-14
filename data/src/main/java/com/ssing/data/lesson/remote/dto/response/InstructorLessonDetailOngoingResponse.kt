package com.ssing.data.lesson.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class InstructorLessonDetailOngoingResponse(
    @SerialName("lessonId") val lessonId: Long,
    @SerialName("lessonStatus") val lessonStatus: LessonStatus,
    @SerialName("statusInfo") val statusInfo: StatusInfo,
    @SerialName("lessonInfo") val lessonInfo: LessonInfo,
    @SerialName("matchingRequests") val matchingRequests: List<MatchingRequest>,
) : InstructorLessonDetailResponse {
    @Serializable
    internal data class StatusInfo(
        @SerialName("serverTime") val serverTime: String,
        @SerialName("actualStartedAt") val actualStartedAt: String,
        @SerialName("expectedEndedAt") val expectedEndedAt: String,
        @SerialName("elapsedSeconds") val elapsedSeconds: Int,
        @SerialName("remainingSeconds") val remainingSeconds: Int,
    )

    @Serializable
    internal data class LessonInfo(
        @SerialName("representativeConsumerNames") val representativeConsumerNames: List<String>,
        @SerialName("totalHeadcount") val totalHeadcount: Int,
        @SerialName("resort") val resort: Resort,
        @SerialName("sport") val sport: String,
        @SerialName("lessonLevel") val lessonLevel: String,
        @SerialName("scheduledAt") val scheduledAt: String,
        @SerialName("scheduledDurationMinutes") val scheduledDurationMinutes: Int,
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