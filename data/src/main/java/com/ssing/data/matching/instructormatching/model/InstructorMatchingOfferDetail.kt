package com.ssing.data.matching.instructormatching.model

sealed interface InstructorMatchingOfferDetail {
    val offerId: Long

    data class Available(
        override val offerId: Long,
        val groupId: Long,
        val offerStatus: String,
        val groupStatus: String,
        val matchingStatus: String,
        val requestSummary: InstructorMatchingRequestSummary,
        val lessonSummary: InstructorMatchingLessonSummary,
        val priceSummary: InstructorMatchingPriceSummary,
        val participants: List<InstructorMatchingParticipant>,
    ) : InstructorMatchingOfferDetail

    data class Stale(
        override val offerId: Long,
    ) : InstructorMatchingOfferDetail
}

data class InstructorMatchingParticipant(
    val age: Int,
    val gender: String,
)
