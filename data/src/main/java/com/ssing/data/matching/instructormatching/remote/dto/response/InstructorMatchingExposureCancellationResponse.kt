package com.ssing.data.matching.instructormatching.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class InstructorMatchingExposureCancellationResponse(
    @SerialName("isExposed") val isExposed: Boolean,
    @SerialName("updatedAt") val updatedAt: String,
)
