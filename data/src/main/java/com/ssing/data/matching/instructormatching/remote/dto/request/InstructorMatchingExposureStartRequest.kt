package com.ssing.data.matching.instructormatching.remote.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class InstructorMatchingExposureStartRequest(
    @SerialName("sport") val sport: String,
    @SerialName("lessonLevels") val lessonLevels: List<String>,
    @SerialName("maxHeadcount") val maxHeadcount: Int,
    @SerialName("equipmentReady") val equipmentReady: Boolean,
    @SerialName("availableDurationMinutes") val availableDurationMinutes: List<Int>,
)
