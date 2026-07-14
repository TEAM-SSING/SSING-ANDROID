package com.ssing.data.matching.instructormatching.repository.impl

import com.ssing.core.network.util.ApiResponseHandler
import com.ssing.data.matching.instructormatching.model.InstructorMatchingExposure
import com.ssing.data.matching.instructormatching.model.InstructorMatchingLessonSummary
import com.ssing.data.matching.instructormatching.model.InstructorMatchingOffer
import com.ssing.data.matching.instructormatching.model.InstructorMatchingOffers
import com.ssing.data.matching.instructormatching.model.InstructorMatchingPriceSummary
import com.ssing.data.matching.instructormatching.model.InstructorMatchingRequestSummary
import com.ssing.data.matching.instructormatching.model.InstructorMatchingResort
import com.ssing.data.matching.instructormatching.remote.datasource.api.InstructorMatchingRemoteDataSource
import com.ssing.data.matching.instructormatching.remote.dto.request.InstructorMatchingExposureStartRequest
import com.ssing.data.matching.instructormatching.remote.dto.response.InstructorMatchingExposureResponse
import com.ssing.data.matching.instructormatching.remote.dto.response.InstructorMatchingOfferResponse
import com.ssing.data.matching.instructormatching.remote.dto.response.InstructorMatchingOffersResponse
import com.ssing.data.matching.instructormatching.repository.api.InstructorMatchingRepository
import javax.inject.Inject

internal class InstructorMatchingRepositoryImpl @Inject constructor(
    private val apiResponseHandler: ApiResponseHandler,
    private val remoteDataSource: InstructorMatchingRemoteDataSource,
) : InstructorMatchingRepository {

    override suspend fun fetchMatchingExposure(): Result<InstructorMatchingExposure> =
        apiResponseHandler.safeApiCall {
            remoteDataSource.getMatchingExposure()
        }.map { it.toModel() }

    override suspend fun fetchMatchingOffers(
        page: Int?,
        size: Int?,
    ): Result<InstructorMatchingOffers> =
        apiResponseHandler.safeApiCall {
            remoteDataSource.getMatchingOffers(page = page, size = size)
        }.map { it.toModel() }

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

    private fun InstructorMatchingExposureResponse.toModel(): InstructorMatchingExposure =
        InstructorMatchingExposure(
            resort = InstructorMatchingResort(
                code = this.resort.code,
                displayName = this.resort.displayName,
            ),
            availableSports = this.availableSports,
        )

    private fun InstructorMatchingOffersResponse.toModel(): InstructorMatchingOffers =
        InstructorMatchingOffers(
            items = this.items.map { it.toModel() },
            currentPage = this.currentPage,
            size = this.size,
            hasNext = this.hasNext,
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
}
