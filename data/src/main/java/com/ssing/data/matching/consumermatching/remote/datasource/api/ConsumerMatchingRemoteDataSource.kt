package com.ssing.data.matching.consumermatching.remote.datasource.api

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.matching.consumermatching.remote.dto.request.ConsumerMatchingConditionRequest
import com.ssing.data.matching.consumermatching.remote.dto.request.ConsumerMatchingConfirmationRequest
import com.ssing.data.matching.consumermatching.remote.dto.response.ConsumerMatchingActiveResponse
import com.ssing.data.matching.consumermatching.remote.dto.response.ConsumerMatchingCancellationResponse
import com.ssing.data.matching.consumermatching.remote.dto.response.ConsumerMatchingConfirmationResponse
import com.ssing.data.matching.consumermatching.remote.dto.response.ConsumerMatchingRequestResponse

internal interface ConsumerMatchingRemoteDataSource {
    suspend fun postMatchingRequest(
        request: ConsumerMatchingConditionRequest,
    ): BaseResponse<ConsumerMatchingRequestResponse>

    suspend fun getMatchingActive(): BaseResponse<ConsumerMatchingActiveResponse>

    suspend fun postMatchingCancellation(
        matchingRequestId: Long,
    ): BaseResponse<ConsumerMatchingCancellationResponse>

    suspend fun patchMatchingConfirmation(
        matchingRequestId: Long,
        request: ConsumerMatchingConfirmationRequest,
    ): BaseResponse<ConsumerMatchingConfirmationResponse>
}
