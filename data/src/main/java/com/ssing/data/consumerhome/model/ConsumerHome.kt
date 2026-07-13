package com.ssing.data.consumerhome.model

data class ConsumerHome(
    val lessonCards: List<LessonCards>,
    val matchingPeopleCount: Long,
    val hasUnreadNotification: Boolean,
)

data class LessonCards(
    val lessonId: Long,
    val remainingDays: Int,
    val displayStatus: DisplayStatus,
    val title: String,
    val scheduledAt: String,
    val sport: Sports,
    val resort: Resort,
)

data class Resort(
    val code: String,
    val displayName: String,
)

enum class DisplayStatus(val label: String,){
    CONFIRMED("CONFIRMED"),
    IN_PROGRESS("IN_PROGRESS"),
}

enum class Sports(val label: String){
    SKI("SKI"),
    SNOWBOARD("SNOWBOARD"),
}
