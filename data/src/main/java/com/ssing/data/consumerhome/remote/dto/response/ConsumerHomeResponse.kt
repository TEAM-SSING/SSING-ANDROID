package com.ssing.data.consumerhome.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ConsumerHomeResponse(
    @SerialName("lessonCards") val lessonCards: List<LessonCardsResponse>,
    @SerialName("matchingPeopleCount") val matchingPeopleCount: Int,
    @SerialName("hasUnreadNotification") val hasUnreadNotification: Boolean,
)
