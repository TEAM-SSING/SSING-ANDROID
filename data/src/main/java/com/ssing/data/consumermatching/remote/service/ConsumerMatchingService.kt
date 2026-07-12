package com.ssing.data.consumermatching.remote.service

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.consumermatching.remote.dto.request.ConsumerMatchingConditionRequest
import com.ssing.data.consumermatching.remote.dto.response.ConsumerMatchingRequestResponse
import retrofit2.http.Body
import retrofit2.http.POST

internal interface ConsumerMatchingService {
    @POST("/api/v1/consumer/matching-requests")
    suspend fun postMatchingRequest(
        @Body request: ConsumerMatchingConditionRequest,
    ): BaseResponse<ConsumerMatchingRequestResponse>
}
