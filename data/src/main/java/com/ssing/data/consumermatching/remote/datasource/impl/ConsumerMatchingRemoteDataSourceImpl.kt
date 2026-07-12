package com.ssing.data.consumermatching.remote.datasource.impl

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.consumermatching.remote.datasource.api.ConsumerMatchingRemoteDataSource
import com.ssing.data.consumermatching.remote.dto.request.ConsumerMatchingConditionRequest
import com.ssing.data.consumermatching.remote.dto.response.ConsumerMatchingRequestResponse
import com.ssing.data.consumermatching.remote.service.ConsumerMatchingService
import javax.inject.Inject

internal class ConsumerMatchingRemoteDataSourceImpl @Inject constructor(
    private val consumerMatchingService: ConsumerMatchingService,
) : ConsumerMatchingRemoteDataSource {

    override suspend fun postMatchingRequest(
        request: ConsumerMatchingConditionRequest,
    ): BaseResponse<ConsumerMatchingRequestResponse> =
        consumerMatchingService.postMatchingRequest(request)
}
