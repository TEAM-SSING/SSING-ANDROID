package com.ssing.data.home.model

data class ConsumerHome(
    val lessonCards: List<LessonCards>,
    val matchingPeopleCount: Long,
    val hasUnreadNotification: Boolean,
)

data class LessonCards(
    val lessonId: Long,
    val remainingDays: Int,
    val displayStatus: String,
    val title: String,
    val scheduledAt: String,
    val sport: String,
    val resort: Resort,
)

data class Resort(
    val code: String,
    val displayName: String,
)
