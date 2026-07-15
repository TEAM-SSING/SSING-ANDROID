package com.ssing.data.matching.consumermatching.remote.payload

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class LessonSummaryPayload(
    @SerialName("resortName") val resortName: String,
    @SerialName("sport") val sport: String,
    @SerialName("level") val level: String,
    @SerialName("durationMinutes") val durationMinutes: Int,
    @SerialName("totalHeadcount") val totalHeadcount: Int,
    @SerialName("startType") val startType: String,
)
