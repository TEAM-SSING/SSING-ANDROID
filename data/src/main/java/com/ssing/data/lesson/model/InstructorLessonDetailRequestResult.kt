package com.ssing.data.lesson.model


sealed interface InstructorLessonDetailRequestResult

data class InstructorLessonDetailBefore(
    val lessonId: Long,
    val confirmedCount: Int,
    val requiredCount: Int,
    val currentActorConfirmed: Boolean,
    val instructorConfirmed: Boolean,
    val representativeConsumerNames: List<String>,
    val totalHeadcount: Int,
    val resortDisplayName: String,
    val sport: String,
    val lessonLevel: String,
    val scheduledAt: String,
    val scheduledDurationMinutes: Int,
    val totalLessonPrice: Int,
    val matchingRequests: List<MatchingRequest>,
) : InstructorLessonDetailRequestResult

data class InstructorLessonDetailOngoing(
    val lessonId: Long,
    val serverTime: String,
    val actualStartedAt: String,
    val expectedEndedAt: String,
    val elapsedSeconds: Int,
    val remainingSeconds: Int,
    val representativeConsumerNames: List<String>,
    val totalHeadcount: Int,
    val resortDisplayName: String,
    val sport: String,
    val lessonLevel: String,
    val scheduledAt: String,
    val scheduledDurationMinutes: Int,
    val totalLessonPrice: Int,
    val matchingRequests: List<MatchingRequest>,
) : InstructorLessonDetailRequestResult

data class InstructorLessonDetailCompleted(
    val lessonId: Long,
    val representativeConsumerNames: List<String>,
    val totalHeadcount: Int,
    val resortDisplayName: String,
    val sport: String,
    val lessonLevel: String,
    val lessonDurationMinutes: Int,
    val actualStartedAt: String,
    val actualEndedAt: String,
    val actualDurationMinutes: Int,
    val totalLessonPrice: Int,
    val matchingRequests: List<MatchingRequest>,
) : InstructorLessonDetailRequestResult

data class InstructorLessonDetailCanceled(
    val lessonId: Long,
    val canceledAt: String,
    val canceledByName: String,
    val cancelReason: String,
    val representativeConsumerNames: List<String>,
    val totalHeadcount: Int,
    val resortDisplayName: String,
    val sport: String,
    val lessonLevel: String,
    val lessonDurationMinutes: Int,
    val totalLessonPrice: Int,
    val matchingRequests: List<MatchingRequest>,
) : InstructorLessonDetailRequestResult

data class MatchingRequest(
    val matchingRequestId: Long,
    val representativeMemberId: Long,
    val representativeMemberName: String,
    val headcount: Int,
    val teamLessonPrice: Int,
    val startConfirmed: Boolean? = null,
    val participants: List<Participant>,
)

data class Participant(
    val participantId: Long,
    val gender: String,
    val age: Int,
)