package com.ssing.data.matching.consumermatching.remote.service

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.matching.consumermatching.remote.dto.request.ConsumerMatchingConditionRequest
import com.ssing.data.matching.consumermatching.remote.dto.request.ConsumerMatchingConfirmationRequest
import com.ssing.data.matching.consumermatching.remote.dto.response.ConsumerMatchingActiveResponse
import com.ssing.data.matching.consumermatching.remote.dto.response.ConsumerMatchingCancellationResponse
import com.ssing.data.matching.consumermatching.remote.dto.response.ConsumerMatchingConfirmationResponse
import com.ssing.data.matching.consumermatching.remote.dto.response.ConsumerMatchingRequestResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

internal interface ConsumerMatchingService {
    @POST("/api/v1/consumer/matching-requests")
    suspend fun postMatchingRequest(
        @Body request: ConsumerMatchingConditionRequest,
    ): BaseResponse<ConsumerMatchingRequestResponse>

    @GET("/api/v1/consumer/matching-requests/active")
    suspend fun getMatchingActive(): BaseResponse<ConsumerMatchingActiveResponse>

    @POST("/api/v1/consumer/matching-requests/{matchingRequestId}/cancellation")
    suspend fun postMatchingCancellation(
        @Path("matchingRequestId") matchingRequestId: Long,
    ): BaseResponse<ConsumerMatchingCancellationResponse>

    @PATCH("/api/v1/consumer/matching-requests/{matchingRequestId}/confirmation")
    suspend fun patchMatchingConfirmation(
        @Path("matchingRequestId") matchingRequestId: Long,
        @Body request: ConsumerMatchingConfirmationRequest,
    ): BaseResponse<ConsumerMatchingConfirmationResponse>
}
