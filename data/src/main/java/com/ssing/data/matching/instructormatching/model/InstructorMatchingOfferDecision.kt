package com.ssing.data.matching.instructormatching.model

data class InstructorMatchingOfferDecision(
    val offerId: Long,
    val offerStatus: String,
    val groupId: Long,
    val groupStatus: String,
    val requesterConfirmationExpiresAt: String?,
)
