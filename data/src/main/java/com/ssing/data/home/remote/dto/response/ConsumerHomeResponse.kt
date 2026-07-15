package com.ssing.data.home.remote.dto.response

import kotlinx.serialization.SerialInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ConsumerHomeResponse(
    @SerialName("lessonCards") val lessonCards: List<LessonCardResponse>,
    @SerialName("matchingPeopleCount") val matchingPeopleCount: Long,
    @SerialName("hasUnreadNotification") val hasUnreadNotification: Boolean,
)

@Serializable
internal data class InstructorHomeResponse(
    @SerialName("lessonCards") val lessonCards: List<LessonCardResponse>,
    @SerialName("matchingPeopleCount") val matchingPeopleCount: Long,
    @SerialName("hasUnreadNotification") val hasUnreadNotification: Boolean,
    @SerialName("reviewSummary") val reviewSummary: ReviewSummaryResponse,
    @SerialName("nickname") val nickname: String,
)

@Serializable
internal data class LessonCardResponse(
    @SerialName("lessonId") val lessonId: Long,
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
    @SerialName("averageRating") val averageRating: Float,
    @SerialName("grade") val grade: Int,
    @SerialName("achievementRate") val achievementRate: Int,
)
