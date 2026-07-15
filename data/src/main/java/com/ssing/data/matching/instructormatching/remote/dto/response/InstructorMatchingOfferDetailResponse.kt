package com.ssing.data.matching.instructormatching.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class InstructorMatchingOfferDetailResponse(
    @SerialName("recoveryState") val recoveryState: String,
    @SerialName("offerId") val offerId: Long,
    @SerialName("groupId") val groupId: Long? = null,
    @SerialName("offerStatus") val offerStatus: String? = null,
    @SerialName("groupStatus") val groupStatus: String? = null,
    @SerialName("matchingStatus") val matchingStatus: String? = null,
    @SerialName("requestSummary") val requestSummary: InstructorMatchingRequestSummaryResponse? = null,
    @SerialName("lessonSummary") val lessonSummary: InstructorMatchingLessonSummaryResponse? = null,
    @SerialName("priceSummary") val priceSummary: InstructorMatchingPriceSummaryResponse? = null,
    @SerialName("participants") val participants: List<InstructorMatchingParticipantResponse> = emptyList(),
)

@Serializable
internal data class InstructorMatchingParticipantResponse(
    @SerialName("age") val age: Int,
    @SerialName("gender") val gender: String,
)
