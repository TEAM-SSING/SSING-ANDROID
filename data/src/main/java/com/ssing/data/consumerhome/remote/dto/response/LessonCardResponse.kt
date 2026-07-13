package com.ssing.data.consumerhome.remote.dto.response

import com.ssing.data.consumerhome.model.DisplayStatus
import com.ssing.data.consumerhome.model.Sports
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class LessonCardResponse(
    @SerialName("lessonId") val lessonId: Long,
    @SerialName("remainingDays") val remainingDays: Int,
    @SerialName("displayStatus") val displayStatus: DisplayStatus,
    @SerialName("title") val title: String,
    @SerialName("sport") val sport: Sports,
    @SerialName("scheduledAt") val scheduledAt: String,
    @SerialName("resort") val resort: ResortResponse,
)