package com.ssing.data.matching.instructormatching.remote.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class InstructorMatchingOfferDecisionRequest(
    @SerialName("decision") val decision: String,
)
