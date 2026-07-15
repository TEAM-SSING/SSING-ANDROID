package com.ssing.data.matching.consumermatching.model

sealed interface ConsumerMatchingActive {
    data class Active(
        val matchingRequestId: Long,
        val matchingStatus: String,
        val requestStatus: String,
        val requestStatusReason: String?,
        val groupId: Long?,
        val groupStatus: String?,
        val itemStatus: String?,
        val offerStatus: String?,
        val paymentStatus: String?,
        val requestSummary: ConsumerMatchingRequestSummary,
        val lessonSummary: ConsumerMatchingLessonSummary?,
        val instructorProfile: ConsumerMatchingInstructorProfile?,
        val progressSummary: ConsumerMatchingProgressSummary?,
        val priceSummary: ConsumerMatchingPriceSummary?,
    ) : ConsumerMatchingActive

    data object None : ConsumerMatchingActive
}

data class ConsumerMatchingRequestSummary(
    val resort: ConsumerMatchingResort,
    val sport: String,
    val lessonLevel: String,
    val headcount: Int,
)

data class ConsumerMatchingResort(
    val code: String,
    val displayName: String,
)

data class ConsumerMatchingLessonSummary(
    val durationMinutes: Int,
    val totalHeadcount: Int,
    val startType: String,
)

data class ConsumerMatchingInstructorProfile(
    val instructorId: Long,
    val name: String,
    val profileImageUrl: String?,
    val gender: String,
    val birthYear: Int,
    val level: Int,
    val careerYears: Int,
    val completedLessonCount: Int,
    val averageRating: Double?,
    val introduction: String,
    val certificateTypes: List<String>,
    val latestReviewContent: String?,
)

data class ConsumerMatchingProgressSummary(
    val acceptedRequesterCount: Int?,
    val totalRequesterCount: Int?,
    val paidRequesterCount: Int?,
)

data class ConsumerMatchingPriceSummary(
    val lessonPriceAmount: Int,
    val resortPassFeeAmount: Int,
    val totalPaymentAmount: Int,
)
