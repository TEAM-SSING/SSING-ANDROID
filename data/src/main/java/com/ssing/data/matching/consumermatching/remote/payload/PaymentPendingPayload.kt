package com.ssing.data.matching.consumermatching.remote.payload

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PaymentPendingPayload(
    @SerialName("matchingRequestPaymentId") val matchingRequestPaymentId: Long,
)
