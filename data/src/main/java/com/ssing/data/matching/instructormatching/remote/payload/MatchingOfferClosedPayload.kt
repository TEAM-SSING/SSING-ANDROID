package com.ssing.data.matching.instructormatching.remote.payload

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class MatchingOfferClosedPayload(
    @SerialName("closedReason") val closedReason: String,
    @SerialName("message") val message: String,
)
