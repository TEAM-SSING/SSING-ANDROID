package com.ssing.data.home.model

data class ConsumerHomeSummary(
    val lessonCards: List<LessonCard>,
    val matchingPeopleCount: Long,
    val hasUnreadNotification: Boolean,
)

data class InstructorHomeSummary(
    val lessonCards: List<LessonCard>,
    val matchingPeopleCount: Long,
    val hasUnreadNotification: Boolean,
    val reviewSummary: ReviewSummary,
    val instructorName: String,
)

data class LessonCard(
    val lessonId: Long,
    val remainingDays: Int,
    val displayStatus: String,
    val title: String,
    val scheduledAt: String,
    val sport: String,
    val resort: Resort,

    )

data class ReviewSummary(
    val averageRating: Float,
    val grade: Int,
    val achievementRate: Int,
)

data class Resort(
    val code: String,
    val displayName: String,
)
