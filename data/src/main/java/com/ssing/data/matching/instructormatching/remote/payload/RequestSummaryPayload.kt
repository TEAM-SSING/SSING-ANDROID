package com.ssing.data.matching.instructormatching.remote.payload

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class RequestSummaryPayload(
    @SerialName("requesterName") val requesterName: String,
    @SerialName("headcount") val headcount: Int,
    @SerialName("matchingRequestCount") val matchingRequestCount: Int,
)
