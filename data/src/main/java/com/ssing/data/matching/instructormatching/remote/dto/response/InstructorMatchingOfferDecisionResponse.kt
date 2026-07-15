package com.ssing.data.matching.instructormatching.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class InstructorMatchingOfferDecisionResponse(
    @SerialName("offerId") val offerId: Long,
    @SerialName("offerStatus") val offerStatus: String,
    @SerialName("groupId") val groupId: Long,
    @SerialName("groupStatus") val groupStatus: String,
    @SerialName("requesterConfirmationExpiresAt") val requesterConfirmationExpiresAt: String? = null,
)
