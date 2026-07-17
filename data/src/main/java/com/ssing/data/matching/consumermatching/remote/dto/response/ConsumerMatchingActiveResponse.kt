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
    @SerialName("requestSummary") val requestSummary: RequestSummary? = null,
    @SerialName("lessonSummary") val lessonSummary: LessonSummary? = null,
    @SerialName("instructorProfile") val instructorProfile: InstructorProfile? = null,
    @SerialName("progressSummary") val progressSummary: ProgressSummary? = null,
    @SerialName("priceSummary") val priceSummary: PriceSummary? = null,
) {
    @Serializable
    internal data class RequestSummary(
        @SerialName("resort") val resort: Resort,
        @SerialName("sport") val sport: String,
        @SerialName("lessonLevel") val lessonLevel: String,
        @SerialName("requesterName") val requesterName: String,
        @SerialName("headcount") val headcount: Int,
        @SerialName("participants") val participants: List<Participant> = emptyList(),
    )

    @Serializable
    internal data class Participant(
        @SerialName("name") val name: String? = null,
        @SerialName("age") val age: Int,
        @SerialName("gender") val gender: String,
    )

    @Serializable
    internal data class Resort(
        @SerialName("code") val code: String,
        @SerialName("displayName") val displayName: String,
    )

    @Serializable
    internal data class LessonSummary(
        @SerialName("durationMinutes") val durationMinutes: Int,
        @SerialName("totalHeadcount") val totalHeadcount: Int,
        @SerialName("startType") val startType: String,
    )

    @Serializable
    internal data class InstructorProfile(
        @SerialName("instructorId") val instructorId: Long,
        @SerialName("name") val name: String,
        @SerialName("profileImageUrl") val profileImageUrl: String? = null,
        @SerialName("gender") val gender: String,
        @SerialName("birthYear") val birthYear: Int,
        @SerialName("level") val level: Int,
        @SerialName("careerYears") val careerYears: Int,
        @SerialName("completedLessonCount") val completedLessonCount: Int,
        @SerialName("averageRating") val averageRating: Double? = null,
        @SerialName("introduction") val introduction: String,
        @SerialName("certificateTypes") val certificateTypes: List<String> = emptyList(),
        @SerialName("latestReview") val latestReview: LatestReview? = null,
    )

    @Serializable
    internal data class LatestReview(
        @SerialName("content") val content: String,
    )

    @Serializable
    internal data class ProgressSummary(
        @SerialName("acceptedRequesterCount") val acceptedRequesterCount: Int? = null,
        @SerialName("totalRequesterCount") val totalRequesterCount: Int? = null,
        @SerialName("paidRequesterCount") val paidRequesterCount: Int? = null,
    )

    @Serializable
    internal data class PriceSummary(
        @SerialName("lessonPriceAmount") val lessonPriceAmount: Int,
        @SerialName("resortPassFeeAmount") val resortPassFeeAmount: Int,
        @SerialName("totalPaymentAmount") val totalPaymentAmount: Int,
    )
}
