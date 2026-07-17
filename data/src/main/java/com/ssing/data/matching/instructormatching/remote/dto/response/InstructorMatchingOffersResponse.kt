package com.ssing.data.matching.instructormatching.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class InstructorMatchingOffersResponse(
    @SerialName("offerId") val offerId: Long? = null,
    @SerialName("matchingSetting") val matchingSetting: InstructorMatchingSettingResponse,
)

@Serializable
internal data class InstructorMatchingSettingResponse(
    @SerialName("isExposed") val isExposed: Boolean,
    @SerialName("resort") val resort: InstructorMatchingResortResponse,
    @SerialName("sport") val sport: String,
    @SerialName("lessonLevels") val lessonLevels: List<String>,
    @SerialName("availableDurationMinutes") val availableDurationMinutes: List<Int>,
    @SerialName("maxHeadcount") val maxHeadcount: Int,
    @SerialName("equipmentReady") val equipmentReady: Boolean,
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
    @SerialName("instructorSettlementAmount") val instructorSettlementAmount: Int? = null,
)
