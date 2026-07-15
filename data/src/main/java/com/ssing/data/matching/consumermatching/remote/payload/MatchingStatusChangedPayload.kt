package com.ssing.data.matching.consumermatching.remote.payload

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class MatchingStatusChangedPayload(
    @SerialName("message") val message: String,
    @SerialName("requestStatusReason") val requestStatusReason: String? = null,
)
