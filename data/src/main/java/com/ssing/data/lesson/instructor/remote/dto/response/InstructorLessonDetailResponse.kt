package com.ssing.data.lesson.instructor.remote.dto.response

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("lessonStatus")
sealed class InstructorLessonDetailResponse {
    abstract val lessonId: Long

    @Serializable
    @SerialName("CONFIRMED")
    data class Confirmed(
        @SerialName("lessonId") override val lessonId: Long,
        @SerialName("statusInfo") val statusInfo: ConfirmedStatusInfoResponse,
        @SerialName("lessonInfo") val lessonInfo: ScheduledLessonInfoResponse,
        @SerialName("matchingRequests") val matchingRequests: List<ConfirmedMatchingRequestResponse>,
    ) : InstructorLessonDetailResponse()

    @Serializable
    @SerialName("IN_PROGRESS")
    data class InProgress(
        @SerialName("lessonId") override val lessonId: Long,
        @SerialName("statusInfo") val statusInfo: InProgressStatusInfoResponse,
        @SerialName("lessonInfo") val lessonInfo: ScheduledLessonInfoResponse,
        @SerialName("matchingRequests") val matchingRequests: List<MatchingRequestResponse>,
    ) : InstructorLessonDetailResponse()

    @Serializable
    @SerialName("COMPLETED")
    data class Completed(
        @SerialName("lessonId") override val lessonId: Long,
        @SerialName("lessonInfo") val lessonInfo: CompletedLessonInfoResponse,
        @SerialName("matchingRequests") val matchingRequests: List<MatchingRequestResponse>,
    ) : InstructorLessonDetailResponse()

    @Serializable
    @SerialName("CANCELED")
    data class Canceled(
        @SerialName("lessonId") override val lessonId: Long,
        @SerialName("cancelInfo") val cancelInfo: CancelInfoResponse,
        @SerialName("lessonInfo") val lessonInfo: CanceledLessonInfoResponse,
        @SerialName("matchingRequests") val matchingRequests: List<MatchingRequestResponse>,
    ) : InstructorLessonDetailResponse()
}

@Serializable
data class ResortResponse(
    @SerialName("code") val code: String,
    @SerialName("displayName") val displayName: String,
)

@Serializable
data class ParticipantResponse(
    @SerialName("participantId") val participantId: Long,
    @SerialName("gender") val gender: String,
    @SerialName("age") val age: Int,
)

@Serializable
data class ConfirmedStatusInfoResponse(
    @SerialName("confirmedCount") val confirmedCount: Int,
    @SerialName("requiredCount") val requiredCount: Int,
    @SerialName("currentActorConfirmed") val currentActorConfirmed: Boolean,
    @SerialName("instructorConfirmed") val instructorConfirmed: Boolean,
)

@Serializable
data class InProgressStatusInfoResponse(
    @SerialName("serverTime") val serverTime: String,
    @SerialName("actualStartedAt") val actualStartedAt: String,
    @SerialName("expectedEndedAt") val expectedEndedAt: String,
    @SerialName("elapsedSeconds") val elapsedSeconds: Int,
    @SerialName("remainingSeconds") val remainingSeconds: Int,
)

@Serializable
data class CancelInfoResponse(
    @SerialName("canceledAt") val canceledAt: String,
    @SerialName("canceledBy") val canceledBy: CanceledByResponse,
    @SerialName("cancelReason") val cancelReason: String,
)

@Serializable
data class CanceledByResponse(
    @SerialName("memberId") val memberId: Long,
    @SerialName("name") val name: String,
)

@Serializable
data class ScheduledLessonInfoResponse(
    @SerialName("representativeConsumerNames") val representativeConsumerNames: List<String>,
    @SerialName("totalHeadcount") val totalHeadcount: Int,
    @SerialName("resort") val resort: ResortResponse,
    @SerialName("sport") val sport: String,
    @SerialName("lessonLevel") val lessonLevel: String,
    @SerialName("scheduledAt") val scheduledAt: String,
    @SerialName("scheduledDurationMinutes") val scheduledDurationMinutes: Int,
    @SerialName("totalLessonPrice") val totalLessonPrice: Int,
)

@Serializable
data class CompletedLessonInfoResponse(
    @SerialName("representativeConsumerNames") val representativeConsumerNames: List<String>,
    @SerialName("totalHeadcount") val totalHeadcount: Int,
    @SerialName("resort") val resort: ResortResponse,
    @SerialName("sport") val sport: String,
    @SerialName("lessonLevel") val lessonLevel: String,
    @SerialName("lessonDurationMinutes") val lessonDurationMinutes: Int,
    @SerialName("actualStartedAt") val actualStartedAt: String,
    @SerialName("actualEndedAt") val actualEndedAt: String,
    @SerialName("actualDurationMinutes") val actualDurationMinutes: Int,
    @SerialName("totalLessonPrice") val totalLessonPrice: Int,
)

@Serializable
data class CanceledLessonInfoResponse(
    @SerialName("representativeConsumerNames") val representativeConsumerNames: List<String>,
    @SerialName("totalHeadcount") val totalHeadcount: Int,
    @SerialName("resort") val resort: ResortResponse,
    @SerialName("sport") val sport: String,
    @SerialName("lessonLevel") val lessonLevel: String,
    @SerialName("lessonDurationMinutes") val lessonDurationMinutes: Int,
    @SerialName("totalLessonPrice") val totalLessonPrice: Int,
)

@Serializable
data class ConfirmedMatchingRequestResponse(
    @SerialName("matchingRequestId") val matchingRequestId: Long,
    @SerialName("representativeMemberId") val representativeMemberId: Long,
    @SerialName("representativeMemberName") val representativeMemberName: String,
    @SerialName("headcount") val headcount: Int,
    @SerialName("teamLessonPrice") val teamLessonPrice: Int,
    @SerialName("startConfirmed") val startConfirmed: Boolean,
    @SerialName("participants") val participants: List<ParticipantResponse>,
)

@Serializable
data class MatchingRequestResponse(
    @SerialName("matchingRequestId") val matchingRequestId: Long,
    @SerialName("representativeMemberId") val representativeMemberId: Long,
    @SerialName("representativeMemberName") val representativeMemberName: String,
    @SerialName("headcount") val headcount: Int,
    @SerialName("teamLessonPrice") val teamLessonPrice: Int,
    @SerialName("participants") val participants: List<ParticipantResponse>,
)