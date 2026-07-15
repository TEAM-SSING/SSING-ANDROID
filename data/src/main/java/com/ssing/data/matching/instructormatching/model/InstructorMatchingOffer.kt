package com.ssing.data.matching.instructormatching.model

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
