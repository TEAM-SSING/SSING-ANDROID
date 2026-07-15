package com.ssing.data.consumerlesson.remote.dto.response

import com.ssing.data.consumerlesson.remote.dto.response.instructorprofile.ConsumerLessonInstructorProfile
import com.ssing.data.consumerlesson.remote.dto.response.lessoninfo.Resort
import com.ssing.data.consumerlesson.remote.dto.response.matchingrequest.Participant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConsumerLessonDetailBeforeResponse(
    @SerialName("lessonId") val lessonId: Long,
    @SerialName("lessonInfo") val lessonInfo: LessonInfo,
    @SerialName("instructorProfile") val instructorProfile: ConsumerLessonInstructorProfile,
    @SerialName("statusInfo") val statusInfo: StatusInfo,
    @SerialName("matchingRequests") val matchingRequests: List<MatchingRequest>,
) : ConsumerLessonDetailResponse {

    @Serializable
    data class StatusInfo(
        @SerialName("confirmedCount") val confirmedCount: Int,
        @SerialName("requiredCount") val requiredCount: Int,
        @SerialName("currentActorConfirmed") val currentActorConfirmed: Boolean,
        @SerialName("instructorConfirmed") val instructorConfirmed: Boolean,
    )

    @Serializable
    data class LessonInfo(
        @SerialName("representativeConsumerNames") val representativeConsumerNames: List<String>,
        @SerialName("totalHeadcount") val totalHeadcount: Int,
        @SerialName("resort") val resort: Resort,
        @SerialName("sport") val sport: String,
        @SerialName("lessonLevel") val lessonLevel: String,
        @SerialName("scheduledAt") val scheduledAt: String,
        @SerialName("scheduledDurationMinutes") val scheduledDurationMinutes: Int,
        @SerialName("myTeamLessonPrice") val myTeamLessonPrice: Int,
    )

    @Serializable
    data class MatchingRequest(
        @SerialName("matchingRequestId") val matchingRequestId: Long,
        @SerialName("representativeMemberId") val representativeMemberId: Long,
        @SerialName("representativeMemberName") val representativeMemberName: String,
        @SerialName("headcount") val headcount: Int,
        @SerialName("startConfirmed") val startConfirmed: Boolean,
        @SerialName("participants") val participants: List<Participant>,
    )
}