package com.ssing.data.payment.remote.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaymentRequest(
    @SerialName("matchingRequestId") val matchingRequestId: Long,
)