package com.ssing.data.matching.consumermatching.repository.impl

import com.ssing.core.network.socket.SocketState
import com.ssing.core.network.socket.matching.MatchingEnvelope
import com.ssing.core.network.util.ApiResponseHandler
import com.ssing.data.matching.common.remote.datasource.api.MatchingSocketDataSource
import com.ssing.data.matching.consumermatching.event.ConsumerMatchingEvent
import com.ssing.data.matching.consumermatching.model.ConsumerMatchingActive
import com.ssing.data.matching.consumermatching.model.ConsumerMatchingInstructorProfile
import com.ssing.data.matching.consumermatching.model.ConsumerMatchingLessonSummary
import com.ssing.data.matching.consumermatching.model.ConsumerMatchingParticipant
import com.ssing.data.matching.consumermatching.model.ConsumerMatchingPriceSummary
import com.ssing.data.matching.consumermatching.model.ConsumerMatchingProgressSummary
import com.ssing.data.matching.consumermatching.model.ConsumerMatchingRequestResult
import com.ssing.data.matching.consumermatching.model.ConsumerMatchingRequestSummary
import com.ssing.data.matching.consumermatching.model.ConsumerMatchingResort
import com.ssing.data.matching.consumermatching.remote.datasource.api.ConsumerMatchingRemoteDataSource
import com.ssing.data.matching.consumermatching.remote.dto.request.ConsumerMatchingConditionRequest
import com.ssing.data.matching.consumermatching.remote.dto.request.ConsumerMatchingConfirmationRequest
import com.ssing.data.matching.consumermatching.remote.dto.request.ConsumerMatchingParticipantRequest
import com.ssing.data.matching.consumermatching.remote.dto.response.ConsumerMatchingActiveResponse
import com.ssing.data.matching.consumermatching.remote.dto.response.ConsumerMatchingRequestResponse
import com.ssing.data.matching.consumermatching.remote.payload.InstructorAcceptedPayload
import com.ssing.data.matching.consumermatching.remote.payload.MatchingCanceledPayload
import com.ssing.data.matching.consumermatching.remote.payload.MatchingConfirmedPayload
import com.ssing.data.matching.consumermatching.remote.payload.MatchingFailedPayload
import com.ssing.data.matching.consumermatching.remote.payload.MatchingStatusChangedPayload
import com.ssing.data.matching.consumermatching.remote.payload.PaymentPendingPayload
import com.ssing.data.matching.consumermatching.remote.payload.PaymentStatusChangedPayload
import com.ssing.data.matching.consumermatching.remote.payload.RequesterConfirmationUpdatedPayload
import com.ssing.data.matching.consumermatching.repository.api.ConsumerMatchingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement
import timber.log.Timber
import javax.inject.Inject

internal class ConsumerMatchingRepositoryImpl @Inject constructor(
    private val apiResponseHandler: ApiResponseHandler,
    private val remoteDataSource: ConsumerMatchingRemoteDataSource,
    private val socketDataSource: MatchingSocketDataSource,
    private val json: Json,
) : ConsumerMatchingRepository {
    override val event: Flow<ConsumerMatchingEvent> = socketDataSource.event
        .filter { envelope -> envelope.recipientRole == "CONSUMER" }
        .mapNotNull { envelope -> envelope.toConsumerMatchingEventOrNull() }

    override val socketState: StateFlow<SocketState> = socketDataSource.socketState

    override fun connect() = socketDataSource.connect()

    override suspend fun disconnect() = socketDataSource.disconnect()

    override suspend fun requestMatching(
        resort: String,
        sport: String,
        lessonLevel: String,
        requestedDurationMinutes: List<Int>,
        participants: List<ConsumerMatchingParticipant>,
        equipmentReady: Boolean,
    ): Result<ConsumerMatchingRequestResult> = apiResponseHandler.safeApiCall {
        remoteDataSource.postMatchingRequest(
            request = ConsumerMatchingConditionRequest(
                resort = resort,
                sport = sport,
                lessonLevel = lessonLevel,
                requestedDurationMinutes = requestedDurationMinutes,
                consumerMatchingParticipantRequests = participants.map { it.toRequest() },
                equipmentReady = equipmentReady,
            )
        )
    }.map { it.toModel() }

    override suspend fun cancelMatching(matchingRequestId: Long): Result<Unit> =
        apiResponseHandler.safeApiCall {
            remoteDataSource.postMatchingCancellation(matchingRequestId)
        }.map { }

    override suspend fun confirmMatching(
        matchingRequestId: Long,
        decision: String,
    ): Result<Unit> =
        apiResponseHandler.safeApiCall {
            remoteDataSource.patchMatchingConfirmation(
                matchingRequestId = matchingRequestId,
                request = ConsumerMatchingConfirmationRequest(decision)
            )
        }.map { }

    override suspend fun getMatchingActive(): Result<Long?> =
        apiResponseHandler.safeApiCall {
            remoteDataSource.getMatchingActive()
        }.map { it.matchingRequestId }

    override suspend fun refetchMatching(): Result<ConsumerMatchingActive> =
        apiResponseHandler.safeApiCall {
            remoteDataSource.getMatchingActive()
        }.map { it.toActiveModel() }

    private fun ConsumerMatchingActiveResponse.toActiveModel(): ConsumerMatchingActive =
        when (recoveryState) {
            RECOVERY_STATE_ACTIVE -> toActiveModelOrNull() ?: ConsumerMatchingActive.None
            else -> ConsumerMatchingActive.None
        }

    private fun ConsumerMatchingActiveResponse.toActiveModelOrNull(): ConsumerMatchingActive.Active? {
        val matchingRequestId = matchingRequestId ?: return logMissingFieldAndReturnNull("matchingRequestId")
        val matchingStatus = matchingStatus ?: return logMissingFieldAndReturnNull("matchingStatus")
        val requestStatus = requestStatus ?: return logMissingFieldAndReturnNull("requestStatus")
        val requestSummary = requestSummary ?: return logMissingFieldAndReturnNull("requestSummary")

        return ConsumerMatchingActive.Active(
            matchingRequestId = matchingRequestId,
            matchingStatus = matchingStatus,
            requestStatus = requestStatus,
            requestStatusReason = requestStatusReason,
            groupId = groupId,
            groupStatus = groupStatus,
            itemStatus = itemStatus,
            offerStatus = offerStatus,
            paymentStatus = paymentStatus,
            requestSummary = ConsumerMatchingRequestSummary(
                resort = ConsumerMatchingResort(
                    code = requestSummary.resort.code,
                    displayName = requestSummary.resort.displayName,
                ),
                sport = requestSummary.sport,
                lessonLevel = requestSummary.lessonLevel,
                headcount = requestSummary.headcount,
            ),
            lessonSummary = lessonSummary?.let {
                ConsumerMatchingLessonSummary(
                    durationMinutes = it.durationMinutes,
                    totalHeadcount = it.totalHeadcount,
                    startType = it.startType,
                )
            },
            instructorProfile = instructorProfile?.let {
                ConsumerMatchingInstructorProfile(
                    instructorId = it.instructorId,
                    name = it.name,
                    profileImageUrl = it.profileImageUrl,
                    gender = it.gender,
                    birthYear = it.birthYear,
                    level = it.level,
                    careerYears = it.careerYears,
                    completedLessonCount = it.completedLessonCount,
                    averageRating = it.averageRating,
                    introduction = it.introduction,
                    certificateTypes = it.certificateTypes,
                    latestReviewContent = it.latestReview?.content,
                )
            },
            progressSummary = progressSummary?.let {
                ConsumerMatchingProgressSummary(
                    acceptedRequesterCount = it.acceptedRequesterCount,
                    totalRequesterCount = it.totalRequesterCount,
                    paidRequesterCount = it.paidRequesterCount,
                )
            },
            priceSummary = priceSummary?.let {
                ConsumerMatchingPriceSummary(
                    lessonPriceAmount = it.lessonPriceAmount,
                    resortPassFeeAmount = it.resortPassFeeAmount,
                    totalPaymentAmount = it.totalPaymentAmount,
                )
            },
        )
    }

    private fun ConsumerMatchingActiveResponse.logMissingFieldAndReturnNull(fieldName: String): Nothing? {
        Timber.w("ACTIVE 응답에 %s 누락, matchingRequestId=%s — NONE으로 폴백", fieldName, matchingRequestId)
        return null
    }

    private fun ConsumerMatchingParticipant.toRequest(): ConsumerMatchingParticipantRequest =
        ConsumerMatchingParticipantRequest(
            age = this.age,
            gender = this.gender,
        )

    private fun ConsumerMatchingRequestResponse.toModel(): ConsumerMatchingRequestResult =
        ConsumerMatchingRequestResult(
            matchingRequestId = this.matchingRequestId,
            matchingStatus = this.matchingStatus,
            requestStatus = this.requestStatus,
            expiresAt = this.expiresAt,
            requestStatusReason = this.requestStatusReason,
        )

    private fun MatchingEnvelope<JsonElement>.toConsumerMatchingEventOrNull(): ConsumerMatchingEvent? =
        runCatching {
            when (eventType) {
                "MATCHING_STATUS_CHANGED" -> {
                    val payload = json.decodeFromJsonElement<MatchingStatusChangedPayload>(payload)
                    ConsumerMatchingEvent.MatchingStatusChangedEvent(
                        matchingRequestId = requireNotNull(matchingRequestId),
                        matchingStatus = requireNotNull(matchingStatus),
                        message = payload.message,
                        groupId = groupId,
                        requestStatusReason = payload.requestStatusReason,
                    )
                }

                "INSTRUCTOR_ACCEPTED" -> {
                    val payload = json.decodeFromJsonElement<InstructorAcceptedPayload>(payload)
                    ConsumerMatchingEvent.InstructorAcceptedEvent(
                        matchingRequestId = requireNotNull(matchingRequestId),
                        groupId = requireNotNull(groupId),
                        matchingStatus = requireNotNull(matchingStatus),
                        instructorProfileId = payload.instructor.instructorProfileId,
                        instructorName = payload.instructor.name,
                        instructorProfileImageUrl = payload.instructor.profileImageUrl,
                        resortName = payload.lessonSummary.resortName,
                        sport = payload.lessonSummary.sport,
                        level = payload.lessonSummary.level,
                        durationMinutes = payload.lessonSummary.durationMinutes,
                        totalHeadcount = payload.lessonSummary.totalHeadcount,
                        startType = payload.lessonSummary.startType,
                    )
                }

                "REQUESTER_CONFIRMATION_UPDATED" -> {
                    val payload =
                        json.decodeFromJsonElement<RequesterConfirmationUpdatedPayload>(payload)
                    ConsumerMatchingEvent.RequesterConfirmationUpdatedEvent(
                        matchingRequestId = requireNotNull(matchingRequestId),
                        groupId = requireNotNull(groupId),
                        matchingStatus = requireNotNull(matchingStatus),
                        acceptedRequesterCount = payload.progressSummary.acceptedRequesterCount,
                        totalRequesterCount = payload.progressSummary.totalRequesterCount,
                        paidRequesterCount = payload.progressSummary.paidRequesterCount,
                    )
                }

                "PAYMENT_PENDING" -> {
                    val payload = json.decodeFromJsonElement<PaymentPendingPayload>(payload)
                    ConsumerMatchingEvent.PaymentPendingEvent(
                        matchingRequestId = requireNotNull(matchingRequestId),
                        groupId = requireNotNull(groupId),
                        matchingStatus = requireNotNull(matchingStatus),
                        matchingRequestPaymentId = payload.matchingRequestPaymentId,
                    )
                }

                "PAYMENT_STATUS_CHANGED" -> {
                    val payload = json.decodeFromJsonElement<PaymentStatusChangedPayload>(payload)
                    ConsumerMatchingEvent.PaymentStatusChangedEvent(
                        matchingRequestId = requireNotNull(matchingRequestId),
                        groupId = requireNotNull(groupId),
                        matchingStatus = requireNotNull(matchingStatus),
                        acceptedRequesterCount = payload.progressSummary.acceptedRequesterCount,
                        totalRequesterCount = payload.progressSummary.totalRequesterCount,
                        paidRequesterCount = payload.progressSummary.paidRequesterCount,
                    )
                }

                "MATCHING_CONFIRMED" -> {
                    val payload = json.decodeFromJsonElement<MatchingConfirmedPayload>(payload)
                    ConsumerMatchingEvent.MatchingConfirmedEvent(
                        matchingRequestId = requireNotNull(matchingRequestId),
                        groupId = requireNotNull(groupId),
                        matchingStatus = requireNotNull(matchingStatus),
                        lessonId = payload.lessonId,
                        resortName = payload.lessonSummary.resortName,
                        sport = payload.lessonSummary.sport,
                        level = payload.lessonSummary.level,
                        durationMinutes = payload.lessonSummary.durationMinutes,
                        totalHeadcount = payload.lessonSummary.totalHeadcount,
                        startType = payload.lessonSummary.startType,
                    )
                }

                "MATCHING_FAILED" -> {
                    val payload = json.decodeFromJsonElement<MatchingFailedPayload>(payload)
                    ConsumerMatchingEvent.MatchingFailedEvent(
                        matchingRequestId = requireNotNull(matchingRequestId),
                        matchingStatus = requireNotNull(matchingStatus),
                        requestStatusReason = payload.requestStatusReason,
                        message = payload.message,
                        groupId = groupId,
                    )
                }

                "MATCHING_CANCELED" -> {
                    val payload = json.decodeFromJsonElement<MatchingCanceledPayload>(payload)
                    ConsumerMatchingEvent.MatchingCanceledEvent(
                        matchingRequestId = requireNotNull(matchingRequestId),
                        matchingStatus = requireNotNull(matchingStatus),
                        requestStatusReason = payload.requestStatusReason,
                        message = payload.message,
                    )
                }

                else -> {
                    Timber.w("알 수 없는 소비자 매칭 소켓 이벤트: $eventType")
                    null
                }
            }
        }.onFailure { Timber.e(it, "소비자 매칭 소켓 이벤트 디코딩 실패 (eventType=$eventType)") }
            .getOrNull()

    private companion object {
        const val RECOVERY_STATE_ACTIVE = "ACTIVE"
    }
}
