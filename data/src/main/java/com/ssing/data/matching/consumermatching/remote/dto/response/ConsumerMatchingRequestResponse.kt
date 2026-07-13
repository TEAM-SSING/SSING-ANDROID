package com.ssing.data.matching.consumermatching.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ConsumerMatchingRequestResponse(
    @SerialName("matchingRequestId") val matchingRequestId: Long,
    @SerialName("matchingStatus") val matchingStatus: String,
    @SerialName("requestStatus") val requestStatus: String,
    @SerialName("expiresAt") val expiresAt: String? = null,
    @SerialName("requestStatusReason") val requestStatusReason: String? = null,
)
