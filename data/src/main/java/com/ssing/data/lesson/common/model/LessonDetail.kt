package com.ssing.data.lesson.common.model

data class LessonInfoBasic(
    val representativeConsumerNames: List<String>,
    val totalHeadcount: Int,
    val resort: Resort,
    val sport: String,
    val lessonLevel: String,
)

data class LessonParticipant(
    val participantId: Long,
    val gender: String,
    val age: Int,
)

data class Resort(
    val code: String,
    val displayName: String,
)

interface MatchingRequestCommon {
    val matchingRequestId: Long
    val representativeMemberId: Long
    val representativeMemberName: String
    val headcount: Int
    val participants: List<LessonParticipant>
}