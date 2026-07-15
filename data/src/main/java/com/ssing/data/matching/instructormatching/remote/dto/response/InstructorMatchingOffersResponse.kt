package com.ssing.data.matching.instructormatching.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class InstructorMatchingOffersResponse(
    @SerialName("items") val items: List<InstructorMatchingOfferResponse>,
    @SerialName("currentPage") val currentPage: Int,
    @SerialName("size") val size: Int,
    @SerialName("hasNext") val hasNext: Boolean,
)

@Serializable
internal data class InstructorMatchingOfferResponse(
    @SerialName("offerId") val offerId: Long,
    @SerialName("groupId") val groupId: Long,
    @SerialName("offerStatus") val offerStatus: String,
    @SerialName("expiresAt") val expiresAt: String? = null,
    @SerialName("requestSummary") val requestSummary: InstructorMatchingRequestSummaryResponse,
    @SerialName("lessonSummary") val lessonSummary: InstructorMatchingLessonSummaryResponse,
    @SerialName("priceSummary") val priceSummary: InstructorMatchingPriceSummaryResponse,
)

@Serializable
internal data class InstructorMatchingRequestSummaryResponse(
    @SerialName("requesterName") val requesterName: String,
    @SerialName("headcount") val headcount: Int,
    @SerialName("matchingRequestCount") val matchingRequestCount: Int,
)

@Serializable
internal data class InstructorMatchingLessonSummaryResponse(
    @SerialName("resort") val resort: InstructorMatchingResortResponse,
    @SerialName("sport") val sport: String,
    @SerialName("level") val level: String,
    @SerialName("durationMinutes") val durationMinutes: Int,
    @SerialName("totalHeadcount") val totalHeadcount: Int,
    @SerialName("startType") val startType: String,
)

@Serializable
internal data class InstructorMatchingPriceSummaryResponse(
    @SerialName("lessonPriceAmount") val lessonPriceAmount: Int,
    @SerialName("resortPassFeeAmount") val resortPassFeeAmount: Int,
    @SerialName("totalPaymentAmount") val totalPaymentAmount: Int,
)
