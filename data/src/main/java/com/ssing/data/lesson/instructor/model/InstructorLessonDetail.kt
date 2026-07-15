package com.ssing.data.lesson.instructor.model

import com.ssing.data.lesson.common.model.LessonInfoBasic
import com.ssing.data.lesson.common.model.LessonParticipant
import com.ssing.data.lesson.common.model.MatchingRequestCommon

sealed interface InstructorLessonDetail {
    val lessonId: Long
    val lessonInfo: InstructorLessonInfo

    data class Confirmed(
        override val lessonId: Long,
        override val lessonInfo: InstructorLessonInfo,
        val confirmedCount: Int,
        val requiredCount: Int,
        val currentActorConfirmed: Boolean,
        val instructorConfirmed: Boolean,
        val scheduledAt: String,
        val scheduledDurationMinutes: Int,
        val matchingRequests: List<InstructorConfirmedMatchingRequest>,
    ) : InstructorLessonDetail

    data class InProgress(
        override val lessonId: Long,
        override val lessonInfo: InstructorLessonInfo,
        val serverTime: String,
        val actualStartedAt: String,
        val expectedEndedAt: String,
        val elapsedSeconds: Int,
        val remainingSeconds: Int,
        val scheduledAt: String,
        val scheduledDurationMinutes: Int,
        val matchingRequests: List<InstructorMatchingRequest>,
    ) : InstructorLessonDetail

    data class Completed(
        override val lessonId: Long,
        override val lessonInfo: InstructorLessonInfo,
        val lessonDurationMinutes: Int,
        val actualStartedAt: String,
        val actualEndedAt: String,
        val actualDurationMinutes: Int,
        val matchingRequests: List<InstructorMatchingRequest>,
    ) : InstructorLessonDetail

    data class Canceled(
        override val lessonId: Long,
        override val lessonInfo: InstructorLessonInfo,
        val canceledAt: String,
        val canceledByMemberId: Long,
        val canceledByName: String,
        val cancelReason: String,
        val lessonDurationMinutes: Int,
        val matchingRequests: List<InstructorMatchingRequest>,
    ) : InstructorLessonDetail
}

data class InstructorLessonInfo(
    val basic: LessonInfoBasic,
    val totalLessonPrice: Int,
)

// confirmed
data class InstructorConfirmedMatchingRequest(
    override val matchingRequestId: Long,
    override val representativeMemberId: Long,
    override val representativeMemberName: String,
    override val headcount: Int,
    override val participants: List<LessonParticipant>,
    val teamLessonPrice: Int,
    val startConfirmed: Boolean,
) : MatchingRequestCommon

// in_progress
data class InstructorMatchingRequest(
    override val matchingRequestId: Long,
    override val representativeMemberId: Long,
    override val representativeMemberName: String,
    override val headcount: Int,
    override val participants: List<LessonParticipant>,
    val teamLessonPrice: Int,
) : MatchingRequestCommon
