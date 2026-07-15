package com.ssing.data.matching.instructormatching.remote.service

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.matching.instructormatching.remote.dto.request.InstructorMatchingExposureStartRequest
import com.ssing.data.matching.instructormatching.remote.dto.request.InstructorMatchingOfferDecisionRequest
import com.ssing.data.matching.instructormatching.remote.dto.response.InstructorMatchingExposureCancellationResponse
import com.ssing.data.matching.instructormatching.remote.dto.response.InstructorMatchingExposureResponse
import com.ssing.data.matching.instructormatching.remote.dto.response.InstructorMatchingExposureStartResponse
import com.ssing.data.matching.instructormatching.remote.dto.response.InstructorMatchingOfferDecisionResponse
import com.ssing.data.matching.instructormatching.remote.dto.response.InstructorMatchingOffersResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

internal interface InstructorMatchingService {
    @GET("/api/v1/instructor/matching-exposure")
    suspend fun getMatchingExposure(): BaseResponse<InstructorMatchingExposureResponse>

    @PUT("/api/v1/instructor/matching-exposure")
    suspend fun putMatchingExposure(
        @Body request: InstructorMatchingExposureStartRequest,
    ): BaseResponse<InstructorMatchingExposureStartResponse>

    @POST("/api/v1/instructor/matching-exposure/cancellation")
    suspend fun postMatchingExposureCancellation(): BaseResponse<InstructorMatchingExposureCancellationResponse>

    @GET("/api/v1/instructor/matching-offers")
    suspend fun getMatchingOffers(): BaseResponse<InstructorMatchingOffersResponse>

    @PATCH("/api/v1/instructor/matching-offers/{offerId}")
    suspend fun patchMatchingOffer(
        @Path("offerId") offerId: Long,
        @Body request: InstructorMatchingOfferDecisionRequest,
    ): BaseResponse<InstructorMatchingOfferDecisionResponse>
}
