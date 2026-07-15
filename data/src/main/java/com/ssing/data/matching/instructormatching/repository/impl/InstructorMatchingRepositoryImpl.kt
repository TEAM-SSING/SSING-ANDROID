package com.ssing.data.matching.instructormatching.repository.impl

import com.ssing.core.network.socket.matching.MatchingEnvelope
import com.ssing.core.network.util.ApiResponseHandler
import com.ssing.data.matching.common.remote.datasource.api.MatchingSocketDataSource
import com.ssing.data.matching.instructormatching.model.InstructorMatchingExposure
import com.ssing.data.matching.instructormatching.model.InstructorMatchingLessonSummary
import com.ssing.data.matching.instructormatching.model.InstructorMatchingOffer
import com.ssing.data.matching.instructormatching.model.InstructorMatchingOfferDecision
import com.ssing.data.matching.instructormatching.model.InstructorMatchingPriceSummary
import com.ssing.data.matching.instructormatching.model.InstructorMatchingRequestSummary
import com.ssing.data.matching.instructormatching.model.InstructorMatchingResort
import com.ssing.data.matching.instructormatching.model.InstructorMatchingSocketEvent
import com.ssing.data.matching.instructormatching.remote.datasource.api.InstructorMatchingRemoteDataSource
import com.ssing.data.matching.instructormatching.remote.dto.request.InstructorMatchingExposureStartRequest
import com.ssing.data.matching.instructormatching.remote.dto.request.InstructorMatchingOfferDecisionRequest
import com.ssing.data.matching.instructormatching.remote.dto.response.InstructorMatchingExposureResponse
import com.ssing.data.matching.instructormatching.remote.dto.response.InstructorMatchingOfferDecisionResponse
import com.ssing.data.matching.instructormatching.remote.dto.response.InstructorMatchingOfferResponse
import com.ssing.data.matching.instructormatching.repository.api.InstructorMatchingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.JsonElement
import javax.inject.Inject

internal class InstructorMatchingRepositoryImpl @Inject constructor(
    private val apiResponseHandler: ApiResponseHandler,
    private val remoteDataSource: InstructorMatchingRemoteDataSource,
    private val socketDataSource: MatchingSocketDataSource,
) : InstructorMatchingRepository {

    override val socketEvents: Flow<InstructorMatchingSocketEvent> =
        socketDataSource.event
            .filter { it.recipientRole == RECIPIENT_INSTRUCTOR }
            .map { it.toSocketEvent() }

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

    private fun MatchingEnvelope<JsonElement>.toSocketEvent(): InstructorMatchingSocketEvent =
        InstructorMatchingSocketEvent(
            eventType = this.eventType,
            offerId = this.offerId,
            groupId = this.groupId,
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
