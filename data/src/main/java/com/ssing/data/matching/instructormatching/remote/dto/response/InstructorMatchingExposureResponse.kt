package com.ssing.data.matching.instructormatching.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class InstructorMatchingExposureResponse(
    @SerialName("resort") val resort: InstructorMatchingResortResponse,
    @SerialName("availableSports") val availableSports: List<String>,
)

@Serializable
internal data class InstructorMatchingResortResponse(
    @SerialName("code") val code: String,
    @SerialName("displayName") val displayName: String,
)
