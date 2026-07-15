package com.ssing.data.lesson.consumer.model

import com.ssing.data.lesson.common.model.LessonInfoBasic
import com.ssing.data.lesson.common.model.LessonParticipant
import com.ssing.data.lesson.common.model.MatchingRequestCommon

sealed interface ConsumerLessonDetail {
    val lessonId: Long
    val lessonInfo: ConsumerLessonInfo
    val instructorProfile: InstructorProfile

    data class Confirmed(
        override val lessonId: Long,
        override val lessonInfo: ConsumerLessonInfo,
        override val instructorProfile: InstructorProfile,
        val confirmedCount: Int,
        val requiredCount: Int,
        val currentActorConfirmed: Boolean,
        val instructorConfirmed: Boolean,
        val scheduledAt: String?,
        val scheduledDurationMinutes: Int,
        val matchingRequests: List<ConsumerConfirmedMatchingRequest>,
    ) : ConsumerLessonDetail

    data class InProgress(
        override val lessonId: Long,
        override val lessonInfo: ConsumerLessonInfo,
        override val instructorProfile: InstructorProfile,
        val serverTime: String,
        val actualStartedAt: String,
        val expectedEndedAt: String,
        val elapsedSeconds: Int,
        val remainingSeconds: Int,
        val scheduledAt: String,
        val scheduledDurationMinutes: Int,
        val matchingRequests: List<ConsumerMatchingRequest>,
    ) : ConsumerLessonDetail

    data class Completed(
        override val lessonId: Long,
        override val lessonInfo: ConsumerLessonInfo,
        override val instructorProfile: InstructorProfile,
        val lessonDurationMinutes: Int,
        val actualStartedAt: String,
        val actualEndedAt: String,
        val actualDurationMinutes: Int,
    ) : ConsumerLessonDetail

    data class Canceled(
        override val lessonId: Long,
        override val lessonInfo: ConsumerLessonInfo,
        override val instructorProfile: InstructorProfile,
        val canceledAt: String,
        val canceledByMemberId: Long,
        val canceledByName: String,
        val cancelReason: String,
        val lessonDurationMinutes: Int,
    ) : ConsumerLessonDetail
}

data class ConsumerLessonInfo(
    val basic: LessonInfoBasic,
    val myTeamLessonPrice: Int,
)

// confirmed
data class ConsumerConfirmedMatchingRequest(
    override val matchingRequestId: Long,
    override val representativeMemberId: Long,
    override val representativeMemberName: String,
    override val headcount: Int,
    override val participants: List<LessonParticipant>,
    val startConfirmed: Boolean,
) : MatchingRequestCommon

// in_progress
data class ConsumerMatchingRequest(
    override val matchingRequestId: Long,
    override val representativeMemberId: Long,
    override val representativeMemberName: String,
    override val headcount: Int,
    override val participants: List<LessonParticipant>,
) : MatchingRequestCommon

data class InstructorProfile(
    val instructorId: Long,
    val name: String,
    val gender: String,
    val birthYear: Int,
    val level: Int,
    val profileImageUrl: String,
)