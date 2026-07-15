package com.ssing.data.matching.consumermatching.remote.payload

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class MatchingConfirmedPayload(
    @SerialName("lessonId") val lessonId: Long,
    @SerialName("lessonSummary") val lessonSummary: LessonSummaryPayload,
)
