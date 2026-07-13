package com.ssing.data.consumerhome.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class LessonCardsResponse(
    @SerialName("lessonId") val lessonId: Long,
    @SerialName("remainingDays") val remainingDays: Int,
    @SerialName("displayStatus") val displayStatus: String,
    @SerialName("title") val title: String,
    @SerialName("sport") val sport: String,
    @SerialName("scheduledAt") val scheduledAt: String,
    @SerialName("resort") val resort: ResortResponse,
)