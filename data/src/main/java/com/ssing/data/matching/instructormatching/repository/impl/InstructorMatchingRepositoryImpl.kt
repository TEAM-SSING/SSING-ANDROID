package com.ssing.data.matching.instructormatching.repository.impl

import com.ssing.core.network.socket.SocketState
import com.ssing.core.network.socket.matching.MatchingEnvelope
import com.ssing.core.network.util.ApiResponseHandler
import com.ssing.data.matching.common.remote.datasource.api.MatchingSocketDataSource
import com.ssing.data.matching.instructormatching.event.InstructorMatchingEvent
import com.ssing.data.matching.instructormatching.model.InstructorMatchingExposure
import com.ssing.data.matching.instructormatching.model.InstructorMatchingLessonSummary
import com.ssing.data.matching.instructormatching.model.InstructorMatchingOffer
import com.ssing.data.matching.instructormatching.model.InstructorMatchingOfferDecision
import com.ssing.data.matching.instructormatching.model.InstructorMatchingPriceSummary
import com.ssing.data.matching.instructormatching.model.InstructorMatchingRequestSummary
import com.ssing.data.matching.instructormatching.model.InstructorMatchingResort
import com.ssing.data.matching.instructormatching.remote.datasource.api.InstructorMatchingRemoteDataSource
import com.ssing.data.matching.instructormatching.remote.dto.request.InstructorMatchingExposureStartRequest
import com.ssing.data.matching.instructormatching.remote.dto.request.InstructorMatchingOfferDecisionRequest
import com.ssing.data.matching.instructormatching.remote.dto.response.InstructorMatchingExposureResponse
import com.ssing.data.matching.instructormatching.remote.dto.response.InstructorMatchingOfferDecisionResponse
import com.ssing.data.matching.instructormatching.remote.dto.response.InstructorMatchingOfferResponse
import com.ssing.data.matching.instructormatching.remote.payload.MatchingCanceledPayload
import com.ssing.data.matching.instructormatching.remote.payload.MatchingConfirmedPayload
import com.ssing.data.matching.instructormatching.remote.payload.MatchingOfferClosedPayload
import com.ssing.data.matching.instructormatching.remote.payload.MatchingOfferReceivedPayload
import com.ssing.data.matching.instructormatching.repository.api.InstructorMatchingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement
import timber.log.Timber
import javax.inject.Inject

internal class InstructorMatchingRepositoryImpl @Inject constructor(
    private val apiResponseHandler: ApiResponseHandler,
    private val remoteDataSource: InstructorMatchingRemoteDataSource,
    private val socketDataSource: MatchingSocketDataSource,
    private val json: Json,
) : InstructorMatchingRepository {

    // 개인 큐(/user/queue/matching) 이벤트 중 수신자가 강사인 것만 eventType별로 디코딩해 노출한다.
    override val event: Flow<InstructorMatchingEvent> =
        socketDataSource.event
            .filter { it.recipientRole == RECIPIENT_INSTRUCTOR }
            .mapNotNull { it.toInstructorMatchingEventOrNull() }

    override val socketState: StateFlow<SocketState> = socketDataSource.socketState

    override fun connectSocket() = socketDataSource.connect()

    override suspend fun disconnectSocket() = socketDataSource.disconnect()

    override suspend fun fetchMatchingExposure(): Result<InstructorMatchingExposure> =
        apiResponseHandler.safeApiCall {
            remoteDataSource.getMatchingExposure()
        }.map { it.toModel() }

    override suspend fun fetchActiveOffer(): Result<InstructorMatchingOffer?> =
        apiResponseHandler.safeApiCall {
            remoteDataSource.getMatchingOffers()
        }.map { it.items.firstOrNull()?.toModel() }

    override suspend fun startMatchingExposure(
        sport: String,
        lessonLevels: List<String>,
        availableDurationMinutes: List<Int>,
        maxHeadcount: Int,
        equipmentReady: Boolean,
    ): Result<Boolean> =
        apiResponseHandler.safeApiCall {
            remoteDataSource.putMatchingExposure(
                request = InstructorMatchingExposureStartRequest(
                    sport = sport,
                    lessonLevels = lessonLevels,
                    availableDurationMinutes = availableDurationMinutes,
                    maxHeadcount = maxHeadcount,
                    equipmentReady = equipmentReady,
                ),
            )
        }.map { it.isExposed }
    private fun MatchingEnvelope<JsonElement>.toInstructorMatchingEventOrNull(): InstructorMatchingEvent? =
        runCatching {
            when (eventType) {
                "MATCHING_OFFER_RECEIVED" -> {
                    val payload = json.decodeFromJsonElement<MatchingOfferReceivedPayload>(payload)
                    InstructorMatchingEvent.OfferReceivedEvent(
                        offerId = requireNotNull(offerId),
                        groupId = requireNotNull(groupId),
                        requesterName = payload.requestSummary.requesterName,
                        headcount = payload.requestSummary.headcount,
                        matchingRequestCount = payload.requestSummary.matchingRequestCount,
                        resortName = payload.lessonSummary.resortName,
                        sport = payload.lessonSummary.sport,
                        level = payload.lessonSummary.level,
                        durationMinutes = payload.lessonSummary.durationMinutes,
                        totalHeadcount = payload.lessonSummary.totalHeadcount,
                        startType = payload.lessonSummary.startType,
                    )
                }

                "MATCHING_OFFER_CLOSED" -> {
                    val payload = json.decodeFromJsonElement<MatchingOfferClosedPayload>(payload)
                    InstructorMatchingEvent.OfferClosedEvent(
                        offerId = requireNotNull(offerId),
                        groupId = requireNotNull(groupId),
                        closedReason = payload.closedReason,
                        message = payload.message,
                    )
                }

                "MATCHING_CONFIRMED" -> {
                    val payload = json.decodeFromJsonElement<MatchingConfirmedPayload>(payload)
                    InstructorMatchingEvent.MatchingConfirmedEvent(
                        offerId = requireNotNull(offerId),
                        groupId = requireNotNull(groupId),
                        lessonId = payload.lessonId,
                        resortName = payload.lessonSummary.resortName,
                        sport = payload.lessonSummary.sport,
                        level = payload.lessonSummary.level,
                        durationMinutes = payload.lessonSummary.durationMinutes,
                        totalHeadcount = payload.lessonSummary.totalHeadcount,
                        startType = payload.lessonSummary.startType,
                    )
                }

                "MATCHING_CANCELED" -> {
                    val payload = json.decodeFromJsonElement<MatchingCanceledPayload>(payload)
                    InstructorMatchingEvent.MatchingCanceledEvent(
                        offerId = requireNotNull(offerId),
                        groupId = requireNotNull(groupId),
                        requestStatusReason = payload.requestStatusReason,
                        message = payload.message,
                    )
                }

                else -> {
                    Timber.w("알 수 없는 강사 매칭 소켓 이벤트: $eventType")
                    null
                }
            }
        }.onFailure { Timber.e(it, "강사 매칭 소켓 이벤트 디코딩 실패 (eventType=$eventType)") }
            .getOrNull()

    override suspend fun cancelMatchingExposure(): Result<Boolean> =
        apiResponseHandler.safeApiCall {
            remoteDataSource.postMatchingExposureCancellation()
        }.map { it.isExposed }

    override suspend fun respondMatchingOffer(
        offerId: Long,
        decision: String,
    ): Result<InstructorMatchingOfferDecision> =
        apiResponseHandler.safeApiCall {
            remoteDataSource.patchMatchingOffer(
                offerId = offerId,
                request = InstructorMatchingOfferDecisionRequest(decision = decision),
            )
        }.map { it.toModel() }

    private fun InstructorMatchingOfferDecisionResponse.toModel(): InstructorMatchingOfferDecision =
        InstructorMatchingOfferDecision(
            offerId = this.offerId,
            offerStatus = this.offerStatus,
            groupId = this.groupId,
            groupStatus = this.groupStatus,
            requesterConfirmationExpiresAt = this.requesterConfirmationExpiresAt,
        )

    private fun InstructorMatchingExposureResponse.toModel(): InstructorMatchingExposure =
        InstructorMatchingExposure(
            resort = InstructorMatchingResort(
                code = this.resort.code,
                displayName = this.resort.displayName,
            ),
            availableSports = this.availableSports,
        )

    private fun InstructorMatchingOfferResponse.toModel(): InstructorMatchingOffer =
        InstructorMatchingOffer(
            offerId = this.offerId,
            groupId = this.groupId,
            offerStatus = this.offerStatus,
            expiresAt = this.expiresAt,
            requestSummary = InstructorMatchingRequestSummary(
                requesterName = this.requestSummary.requesterName,
                headcount = this.requestSummary.headcount,
                matchingRequestCount = this.requestSummary.matchingRequestCount,
            ),
            lessonSummary = InstructorMatchingLessonSummary(
                resort = InstructorMatchingResort(
                    code = this.lessonSummary.resort.code,
                    displayName = this.lessonSummary.resort.displayName,
                ),
                sport = this.lessonSummary.sport,
                level = this.lessonSummary.level,
                durationMinutes = this.lessonSummary.durationMinutes,
                totalHeadcount = this.lessonSummary.totalHeadcount,
                startType = this.lessonSummary.startType,
            ),
            priceSummary = InstructorMatchingPriceSummary(
                lessonPriceAmount = this.priceSummary.lessonPriceAmount,
                resortPassFeeAmount = this.priceSummary.resortPassFeeAmount,
                totalPaymentAmount = this.priceSummary.totalPaymentAmount,
            ),
        )

    companion object {
        private const val RECIPIENT_INSTRUCTOR = "INSTRUCTOR"
    }
}
