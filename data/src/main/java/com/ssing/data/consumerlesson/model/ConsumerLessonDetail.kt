package com.ssing.data.consumerlesson.model

sealed interface ConsumerLessonDetail {
    val lessonId: Long
    val lessonInfo: LessonInfo
    val instructorProfile: InstructorProfile

    data class Confirmed(
        override val lessonId: Long,
        override val lessonInfo: LessonInfo,
        override val instructorProfile: InstructorProfile,
        val confirmedCount: Int,
        val requiredCount: Int,
        val currentActorConfirmed: Boolean,
        val instructorConfirmed: Boolean,
        val scheduledDurationMinutes: Int,
        val lessonMatchingRequest: List<LessonMatchingRequest>,
    ) : ConsumerLessonDetail

    data class InProgress(
        override val lessonId: Long,
        override val lessonInfo: LessonInfo,
        override val instructorProfile: InstructorProfile,
        val serverTime: String,
        val actualStartedAt: String,
        val expectedEndedAt: String,
        val elapsedSeconds: Int,
        val remainingSeconds: Int,
        val scheduledDurationMinutes: Int,
        val lessonMatchingRequest: List<LessonMatchingRequest>,
    ): ConsumerLessonDetail

    data class Completed(
        override val lessonId: Long,
        override val lessonInfo: LessonInfo,
        override val instructorProfile: InstructorProfile,
        val lessonDurationMinutes: Int,
        val actualStartedAt: String,
        val actualEndedAt: String,
        val actualDurationMinutes: Int,
    ): ConsumerLessonDetail

    data class Canceled(
        override val lessonId: Long,
        override val lessonInfo: LessonInfo,
        override val instructorProfile: InstructorProfile,
        val canceledAt: String,
        val canceledByMemberId: Long,
        val canceledByName: String,
        val cancelReason: String,
        val lessonDurationMinutes: Int,
    ): ConsumerLessonDetail
}

data class LessonInfo(
    val representativeConsumerNames: List<String>,
    val totalHeadcount: Int,
    val resortCode: String,
    val resortDisplayName: String,
    val sport: String,
    val lessonLevel: String,
    val myTeamLessonPrice: Int,
)

data class LessonMatchingRequest(
    val matchingRequestId: Long,
    val representativeMemberId: Long,
    val representativeMemberName: String,
    val headcount: Int,
    val startConfirmed: Boolean,
    val participants: List<LessonParticipant>,
)

data class LessonParticipant(
    val participantId: Long,
    val gender: String,
    val age: Int,
)

data class InstructorProfile(
    val instructorId: Long,
    val name: String,
    val gender: String,
    val birthYear: Int,
    val level: Int,
    val profileImageUrl: String,
)