package com.ssing.data.home.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ConsumerHomeResponse(
    @SerialName("lessonCards") val lessonCards: List<ConsumerLessonCardResponse>,
    @SerialName("matchingPeopleCount") val matchingPeopleCount: Long,
    @SerialName("hasUnreadNotification") val hasUnreadNotification: Boolean,
)

@Serializable
internal data class InstructorHomeResponse(
    @SerialName("lessonCards") val lessonCards: List<InstructorLessonCardResponse>,
    @SerialName("matchingPeopleCount") val matchingPeopleCount: Long,
    @SerialName("hasUnreadNotification") val hasUnreadNotification: Boolean,
    @SerialName("reviewSummary") val reviewSummary: ReviewSummaryResponse,
    @SerialName("instructorName") val instructorName: String,
)

@Serializable
internal data class ConsumerLessonCardResponse(
    @SerialName("lessonId") val lessonId: Long,
    @SerialName("remainingDays") val remainingDays: Int,
    @SerialName("displayStatus") val displayStatus: String,
    @SerialName("title") val title: String,
    @SerialName("sport") val sport: String,
    @SerialName("scheduledAt") val scheduledAt: String,
    @SerialName("resort") val resort: ResortResponse,
)

@Serializable
internal data class InstructorLessonCardResponse(
    @SerialName("lessonId") val lessonId: Long?,
    @SerialName("offerId") val offerId: Long?,
    @SerialName("remainingDays") val remainingDays: Int,
    @SerialName("displayStatus") val displayStatus: String,
    @SerialName("title") val title: String,
    @SerialName("sport") val sport: String,
    @SerialName("scheduledAt") val scheduledAt: String,
    @SerialName("resort") val resort: ResortResponse,
)

@Serializable
internal data class ResortResponse(
    @SerialName("code") val code: String,
    @SerialName("displayName") val displayName: String,
)

@Serializable
internal data class ReviewSummaryResponse(
    @SerialName("averageRating") val averageRating: Float = 0f,
    @SerialName("grade") val grade: Int = 1,
    @SerialName("achievementRate") val achievementRate: Int = 0,
)
