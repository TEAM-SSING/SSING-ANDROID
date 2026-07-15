package com.ssing.data.matching.consumermatching.remote.payload

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ProgressSummaryPayload(
    @SerialName("acceptedRequesterCount") val acceptedRequesterCount: Int? = null,
    @SerialName("totalRequesterCount") val totalRequesterCount: Int? = null,
    @SerialName("paidRequesterCount") val paidRequesterCount: Int? = null,
)
