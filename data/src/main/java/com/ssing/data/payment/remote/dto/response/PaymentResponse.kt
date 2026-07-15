package com.ssing.data.payment.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PaymentResponse (
    @SerialName("matchingRequestId") val matchingRequestId: Long,
    @SerialName("matchingStatus") val matchingStatus: String,
    @SerialName("paymentStatus") val paymentStatus: String,
    @SerialName("groupId") val groupId: Long,
    @SerialName("groupStatus") val groupStatus: String,
    @SerialName("paidCount") val paidCount: Int,
    @SerialName("requiredCount") val requiredCount: Int,
    @SerialName("lessonId") val lessonId: Long? = null,
    @SerialName("expiresAt") val expiresAt: String? = null,
    )
