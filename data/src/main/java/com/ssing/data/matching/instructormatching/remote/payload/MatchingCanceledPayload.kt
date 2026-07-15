package com.ssing.data.matching.instructormatching.remote.payload

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class MatchingCanceledPayload(
    @SerialName("requestStatusReason") val requestStatusReason: String,
    @SerialName("message") val message: String,
)
