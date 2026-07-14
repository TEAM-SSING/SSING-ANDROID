package com.ssing.data.matching.instructormatching.event

sealed interface InstructorMatchingEvent {

    data class OfferReceivedEvent(
        val offerId: Long,
        val groupId: Long,
        val requesterName: String,
        val headcount: Int,
        val matchingRequestCount: Int,
        val resortName: String,
        val sport: String,
        val level: String,
        val durationMinutes: Int,
        val totalHeadcount: Int,
        val startType: String,
    ) : InstructorMatchingEvent

    data class OfferClosedEvent(
        val offerId: Long,
        val groupId: Long,
        val closedReason: String,
        val message: String,
    ) : InstructorMatchingEvent

    data class MatchingConfirmedEvent(
        val offerId: Long,
        val groupId: Long,
        val lessonId: Long,
        val resortName: String,
        val sport: String,
        val level: String,
        val durationMinutes: Int,
        val totalHeadcount: Int,
        val startType: String,
    ) : InstructorMatchingEvent

    data class MatchingCanceledEvent(
        val offerId: Long,
        val groupId: Long,
        val requestStatusReason: String,
        val message: String,
    ) : InstructorMatchingEvent
}
