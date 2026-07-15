package com.ssing.data.matching.consumermatching.remote.payload

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PaymentStatusChangedPayload(
    @SerialName("progressSummary") val progressSummary: ProgressSummaryPayload,
)
