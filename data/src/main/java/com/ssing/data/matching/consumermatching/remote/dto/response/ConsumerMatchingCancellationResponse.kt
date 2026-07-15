package com.ssing.data.matching.consumermatching.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ConsumerMatchingCancellationResponse(
    @SerialName("matchingRequestId") val matchingRequestId: Long,
    @SerialName("matchingStatus") val matchingStatus: String,
    @SerialName("requestStatus") val requestStatus: String,
    @SerialName("requestStatusReason") val requestStatusReason: String,
    @SerialName("canceledAt") val canceledAt: String,
)
