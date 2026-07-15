package com.ssing.data.matching.consumermatching.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ConsumerMatchingConfirmationResponse(
    @SerialName("matchingRequestId") val matchingRequestId: Long,
    @SerialName("matchingStatus") val matchingStatus: String,
    @SerialName("confirmationStatus") val confirmationStatus: String,
    @SerialName("requestStatus") val requestStatus: String,
    @SerialName("requestStatusReason") val requestStatusReason: String? = null,
    @SerialName("groupId") val groupId: Long? = null,
    @SerialName("groupStatus") val groupStatus: String? = null,
    @SerialName("itemStatus") val itemStatus: String? = null,
    @SerialName("confirmedCount") val confirmedCount: Int? = null,
    @SerialName("requiredCount") val requiredCount: Int? = null,
    @SerialName("expiresAt") val expiresAt: String? = null,
    @SerialName("priceSummary") val priceSummary: PriceSummary? = null,
)

@Serializable
internal data class PriceSummary(
    @SerialName("lessonPriceAmount") val lessonPriceAmount: Int,
    @SerialName("resortPassFeeAmount") val resortPassFeeAmount: Int,
    @SerialName("totalPaymentAmount") val totalPaymentAmount: Int,
)
