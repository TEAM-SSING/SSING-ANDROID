package com.ssing.data.matching.instructormatching.model

data class InstructorMatchingOffer(
    val offerId: Long,
    val groupId: Long,
    val offerStatus: String,
    val expiresAt: String?,
    val requestSummary: InstructorMatchingRequestSummary,
    val lessonSummary: InstructorMatchingLessonSummary,
    val priceSummary: InstructorMatchingPriceSummary,
)

data class InstructorMatchingRequestSummary(
    val requesterName: String,
    val headcount: Int,
    val matchingRequestCount: Int,
)

data class InstructorMatchingLessonSummary(
    val resort: InstructorMatchingResort,
    val sport: String,
    val level: String,
    val durationMinutes: Int,
    val totalHeadcount: Int,
    val startType: String,
)

data class InstructorMatchingPriceSummary(
    val lessonPriceAmount: Int,
    val resortPassFeeAmount: Int,
    val totalPaymentAmount: Int,
)
