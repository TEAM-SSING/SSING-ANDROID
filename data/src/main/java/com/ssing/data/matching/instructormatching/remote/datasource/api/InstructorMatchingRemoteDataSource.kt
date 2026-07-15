package com.ssing.data.matching.instructormatching.remote.datasource.api

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.matching.instructormatching.remote.dto.request.InstructorMatchingExposureStartRequest
import com.ssing.data.matching.instructormatching.remote.dto.request.InstructorMatchingOfferDecisionRequest
import com.ssing.data.matching.instructormatching.remote.dto.response.InstructorMatchingExposureCancellationResponse
import com.ssing.data.matching.instructormatching.remote.dto.response.InstructorMatchingExposureResponse
import com.ssing.data.matching.instructormatching.remote.dto.response.InstructorMatchingExposureStartResponse
import com.ssing.data.matching.instructormatching.remote.dto.response.InstructorMatchingOfferDecisionResponse
import com.ssing.data.matching.instructormatching.remote.dto.response.InstructorMatchingOfferDetailResponse
import com.ssing.data.matching.instructormatching.remote.dto.response.InstructorMatchingOffersResponse

internal interface InstructorMatchingRemoteDataSource {
    suspend fun getMatchingExposure(): BaseResponse<InstructorMatchingExposureResponse>

    suspend fun getMatchingOffers(): BaseResponse<InstructorMatchingOffersResponse>

    suspend fun getMatchingOfferDetail(offerId: Long): BaseResponse<InstructorMatchingOfferDetailResponse>

    suspend fun putMatchingExposure(
        request: InstructorMatchingExposureStartRequest,
    ): BaseResponse<InstructorMatchingExposureStartResponse>

    suspend fun postMatchingExposureCancellation(): BaseResponse<InstructorMatchingExposureCancellationResponse>

    suspend fun patchMatchingOffer(
        offerId: Long,
        request: InstructorMatchingOfferDecisionRequest,
    ): BaseResponse<InstructorMatchingOfferDecisionResponse>
}
