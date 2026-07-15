package com.ssing.data.matching.consumermatching.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ConsumerMatchingActiveResponse(
    @SerialName("recoveryState") val recoveryState: String,
    @SerialName("matchingRequestId") val matchingRequestId: Long? = null,
    @SerialName("matchingStatus") val matchingStatus: String? = null,
    @SerialName("requestStatus") val requestStatus: String? = null,
    @SerialName("requestStatusReason") val requestStatusReason: String? = null,
    @SerialName("groupId") val groupId: Long? = null,
    @SerialName("groupStatus") val groupStatus: String? = null,
    @SerialName("itemStatus") val itemStatus: String? = null,
    @SerialName("offerStatus") val offerStatus: String? = null,
    @SerialName("paymentStatus") val paymentStatus: String? = null,
    @SerialName("progressSummary") val progressSummary: ProgressSummary? = null,
    @SerialName("instructorProfile") val instructorProfile: InstructorProfile? = null,
    @SerialName("priceSummary") val priceSummary: PriceSummary? = null,
    @SerialName("expiresAt") val expiresAt: String? = null,
) {
    @Serializable
    internal data class ProgressSummary(
        @SerialName("acceptedRequesterCount") val acceptedRequesterCount: Int? = null,
        @SerialName("totalRequesterCount") val totalRequesterCount: Int? = null,
        @SerialName("paidRequesterCount") val paidRequesterCount: Int? = null,
    )

    @Serializable
    internal data class InstructorProfile(
        @SerialName("instructorId") val instructorId: Long,
        @SerialName("name") val name: String,
        @SerialName("gender") val gender: String,
        @SerialName("birthYear") val birthYear: Int,
        @SerialName("level") val level: Int,
    )

    @Serializable
    internal data class PriceSummary(
        @SerialName("lessonPriceAmount") val lessonPriceAmount: Int,
        @SerialName("resortPassFeeAmount") val resortPassFeeAmount: Int,
        @SerialName("totalPaymentAmount") val totalPaymentAmount: Int,
    )
}
