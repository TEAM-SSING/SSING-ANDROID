package com.ssing.data.payment.model

data class PaymentSummary(
    val matchingRequestId: Long,
    val matchingStatus: String,
    val paymentStatus: String,
    val groupId: Long,
    val groupStatus: String,
    val paidCount: Int,
    val requiredCount: Int,
    val lessonId: Long? = null,
    val expiresAt: String? = null,
)
