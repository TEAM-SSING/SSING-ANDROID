package com.ssing.data.matching.consumermatching.model

data class ConsumerMatchingRequestResult(
    val matchingRequestId: Long,
    val matchingStatus: String,
    val requestStatus: String,
    val expiresAt: String? = null,
    val requestStatusReason: String? = null,
)
