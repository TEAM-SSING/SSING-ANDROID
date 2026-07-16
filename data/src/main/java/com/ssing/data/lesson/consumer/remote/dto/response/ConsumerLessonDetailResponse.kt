package com.ssing.data.lesson.consumer.remote.dto.response

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("lessonStatus")
sealed class ConsumerLessonDetailResponse {
    abstract val lessonId: Long

    @Serializable
    @SerialName("CONFIRMED")
    data class Confirmed(
        @SerialName("lessonId") override val lessonId: Long,
        @SerialName("statusInfo") val statusInfo: ConsumerConfirmedStatusInfoResponse,
        @SerialName("lessonInfo") val lessonInfo: ConsumerScheduledLessonInfoResponse,
        @SerialName("instructorProfile") val instructorProfile: InstructorProfileResponse,
        @SerialName("matchingRequests") val matchingRequests: List<ConsumerConfirmedMatchingRequestResponse>,
    ) : ConsumerLessonDetailResponse()

    @Serializable
    @SerialName("IN_PROGRESS")
    data class InProgress(
        @SerialName("lessonId") override val lessonId: Long,
        @SerialName("statusInfo") val statusInfo: ConsumerInProgressStatusInfoResponse,
        @SerialName("lessonInfo") val lessonInfo: ConsumerScheduledLessonInfoResponse,
        @SerialName("instructorProfile") val instructorProfile: InstructorProfileResponse,
        @SerialName("matchingRequests") val matchingRequests: List<ConsumerMatchingRequestResponse>,
    ) : ConsumerLessonDetailResponse()

    @Serializable
    @SerialName("COMPLETED")
    data class Completed(
        @SerialName("lessonId") override val lessonId: Long,
        @SerialName("lessonInfo") val lessonInfo: ConsumerCompletedLessonInfoResponse,
        @SerialName("instructorProfile") val instructorProfile: InstructorProfileResponse,
    ) : ConsumerLessonDetailResponse()

    @Serializable
    @SerialName("CANCELED")
    data class Canceled(
        @SerialName("lessonId") override val lessonId: Long,
        @SerialName("cancelInfo") val cancelInfo: ConsumerCancelInfoResponse,
        @SerialName("lessonInfo") val lessonInfo: ConsumerCanceledLessonInfoResponse,
        @SerialName("instructorProfile") val instructorProfile: InstructorProfileResponse,
    ) : ConsumerLessonDetailResponse()
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
data class InstructorProfileResponse(
    @SerialName("instructorId") val instructorId: Long,
    @SerialName("name") val name: String,
    @SerialName("gender") val gender: String,
    @SerialName("birthYear") val birthYear: Int,
    @SerialName("level") val level: Int,
    @SerialName("profileImageUrl") val profileImageUrl: String? = null,
)

@Serializable
data class ConsumerConfirmedStatusInfoResponse(
    @SerialName("confirmedCount") val confirmedCount: Int,
    @SerialName("requiredCount") val requiredCount: Int,
    @SerialName("currentActorConfirmed") val currentActorConfirmed: Boolean,
    @SerialName("instructorConfirmed") val instructorConfirmed: Boolean,
)

@Serializable
data class ConsumerInProgressStatusInfoResponse(
    @SerialName("serverTime") val serverTime: String,
    @SerialName("actualStartedAt") val actualStartedAt: String,
    @SerialName("expectedEndedAt") val expectedEndedAt: String,
    @SerialName("elapsedSeconds") val elapsedSeconds: Int,
    @SerialName("remainingSeconds") val remainingSeconds: Int,
)

@Serializable
data class ConsumerCancelInfoResponse(
    @SerialName("canceledAt") val canceledAt: String,
    @SerialName("canceledBy") val canceledBy: ConsumerCanceledByResponse,
    @SerialName("cancelReason") val cancelReason: String,
)

@Serializable
data class ConsumerCanceledByResponse(
    @SerialName("memberId") val memberId: Long,
    @SerialName("name") val name: String,
)

@Serializable
data class ConsumerScheduledLessonInfoResponse(
    @SerialName("representativeConsumerNames") val representativeConsumerNames: List<String>,
    @SerialName("totalHeadcount") val totalHeadcount: Int,
    @SerialName("resort") val resort: ResortResponse,
    @SerialName("sport") val sport: String,
    @SerialName("lessonLevel") val lessonLevel: String,
    @SerialName("scheduledAt") val scheduledAt: String,
    @SerialName("scheduledDurationMinutes") val scheduledDurationMinutes: Int,
    @SerialName("myTeamLessonPrice") val myTeamLessonPrice: Int,
)

@Serializable
data class ConsumerCompletedLessonInfoResponse(
    @SerialName("representativeConsumerNames") val representativeConsumerNames: List<String>,
    @SerialName("totalHeadcount") val totalHeadcount: Int,
    @SerialName("resort") val resort: ResortResponse,
    @SerialName("sport") val sport: String,
    @SerialName("lessonLevel") val lessonLevel: String,
    @SerialName("lessonDurationMinutes") val lessonDurationMinutes: Int,
    @SerialName("actualStartedAt") val actualStartedAt: String,
    @SerialName("actualEndedAt") val actualEndedAt: String,
    @SerialName("actualDurationMinutes") val actualDurationMinutes: Int,
    @SerialName("myTeamLessonPrice") val myTeamLessonPrice: Int,
)

@Serializable
data class ConsumerCanceledLessonInfoResponse(
    @SerialName("representativeConsumerNames") val representativeConsumerNames: List<String>,
    @SerialName("totalHeadcount") val totalHeadcount: Int,
    @SerialName("resort") val resort: ResortResponse,
    @SerialName("sport") val sport: String,
    @SerialName("lessonLevel") val lessonLevel: String,
    @SerialName("lessonDurationMinutes") val lessonDurationMinutes: Int,
    @SerialName("myTeamLessonPrice") val myTeamLessonPrice: Int,
)

@Serializable
data class ConsumerConfirmedMatchingRequestResponse(
    @SerialName("matchingRequestId") val matchingRequestId: Long,
    @SerialName("representativeMemberId") val representativeMemberId: Long,
    @SerialName("representativeMemberName") val representativeMemberName: String,
    @SerialName("headcount") val headcount: Int,
    @SerialName("startConfirmed") val startConfirmed: Boolean,
    @SerialName("participants") val participants: List<ParticipantResponse>,
)

@Serializable
data class ConsumerMatchingRequestResponse(
    @SerialName("matchingRequestId") val matchingRequestId: Long,
    @SerialName("representativeMemberId") val representativeMemberId: Long,
    @SerialName("representativeMemberName") val representativeMemberName: String,
    @SerialName("headcount") val headcount: Int,
    @SerialName("participants") val participants: List<ParticipantResponse>,
)