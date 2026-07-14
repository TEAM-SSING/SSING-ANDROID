package com.ssing.data.matching.consumermatching.remote.datasource.impl

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.matching.consumermatching.remote.datasource.api.ConsumerMatchingRemoteDataSource
import com.ssing.data.matching.consumermatching.remote.dto.request.ConsumerMatchingConditionRequest
import com.ssing.data.matching.consumermatching.remote.dto.request.ConsumerMatchingConfirmationRequest
import com.ssing.data.matching.consumermatching.remote.dto.response.ConsumerMatchingCancellationResponse
import com.ssing.data.matching.consumermatching.remote.dto.response.ConsumerMatchingConfirmationResponse
import com.ssing.data.matching.consumermatching.remote.dto.response.ConsumerMatchingRequestResponse
import com.ssing.data.matching.consumermatching.remote.service.ConsumerMatchingService
import javax.inject.Inject

internal class ConsumerMatchingRemoteDataSourceImpl @Inject constructor(
    private val consumerMatchingService: ConsumerMatchingService,
) : ConsumerMatchingRemoteDataSource {

    override suspend fun postMatchingRequest(
        request: ConsumerMatchingConditionRequest,
    ): BaseResponse<ConsumerMatchingRequestResponse> =
        consumerMatchingService.postMatchingRequest(request)

    override suspend fun postMatchingCancellation(
        matchingRequestId: Long,
    ): BaseResponse<ConsumerMatchingCancellationResponse> =
        consumerMatchingService.postMatchingCancellation(matchingRequestId)

    override suspend fun patchMatchingConfirmation(
        matchingRequestId: Long,
        request: ConsumerMatchingConfirmationRequest,
    ): BaseResponse<ConsumerMatchingConfirmationResponse> =
        consumerMatchingService.patchMatchingConfirmation(
            matchingRequestId = matchingRequestId,
            request = request,
        )
}
