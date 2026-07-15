package com.ssing.data.matching.consumermatching.event

sealed interface ConsumerMatchingEvent {

    data class MatchingStatusChangedEvent(
        val matchingRequestId: Long,
        val matchingStatus: String,
        val message: String,
        val groupId: Long? = null,
        val requestStatusReason: String? = null,
    ) : ConsumerMatchingEvent

    data class InstructorAcceptedEvent(
        val matchingRequestId: Long,
        val groupId: Long,
        val matchingStatus: String,
        val instructorProfileId: Long,
        val instructorName: String,
        val resortName: String,
        val sport: String,
        val level: String,
        val durationMinutes: Int,
        val totalHeadcount: Int,
        val startType: String,
        val instructorProfileImageUrl: String? = null,
    ) : ConsumerMatchingEvent

    data class RequesterConfirmationUpdatedEvent(
        val matchingRequestId: Long,
        val groupId: Long,
        val matchingStatus: String,
        val acceptedRequesterCount: Int? = null,
        val totalRequesterCount: Int? = null,
        val paidRequesterCount: Int? = null,
    ) : ConsumerMatchingEvent

    data class PaymentPendingEvent(
        val matchingRequestId: Long,
        val groupId: Long,
        val matchingStatus: String,
        val matchingRequestPaymentId: Long,
    ) : ConsumerMatchingEvent

    data class PaymentStatusChangedEvent(
        val matchingRequestId: Long,
        val groupId: Long,
        val matchingStatus: String,
        val acceptedRequesterCount: Int? = null,
        val totalRequesterCount: Int? = null,
        val paidRequesterCount: Int? = null,
    ) : ConsumerMatchingEvent

    data class MatchingConfirmedEvent(
        val matchingRequestId: Long,
        val groupId: Long,
        val matchingStatus: String,
        val lessonId: Long,
        val resortName: String,
        val sport: String,
        val level: String,
        val durationMinutes: Int,
        val totalHeadcount: Int,
        val startType: String,
    ) : ConsumerMatchingEvent

    data class MatchingFailedEvent(
        val matchingRequestId: Long,
        val matchingStatus: String,
        val requestStatusReason: String,
        val message: String,
        val groupId: Long? = null,
    ) : ConsumerMatchingEvent

    data class MatchingCanceledEvent(
        val matchingRequestId: Long,
        val matchingStatus: String,
        val requestStatusReason: String,
        val message: String,
    ) : ConsumerMatchingEvent
}
