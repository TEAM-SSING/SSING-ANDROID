package com.ssing.data.matching.instructormatching.remote.payload

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class MatchingOfferReceivedPayload(
    @SerialName("requestSummary") val requestSummary: RequestSummaryPayload,
    @SerialName("lessonSummary") val lessonSummary: LessonSummaryPayload,
)
