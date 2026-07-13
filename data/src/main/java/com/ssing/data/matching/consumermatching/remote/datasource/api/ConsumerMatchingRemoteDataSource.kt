package com.ssing.data.matching.consumermatching.remote.datasource.api

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.matching.consumermatching.remote.dto.request.ConsumerMatchingConditionRequest
import com.ssing.data.matching.consumermatching.remote.dto.response.ConsumerMatchingRequestResponse

internal interface ConsumerMatchingRemoteDataSource {
    suspend fun postMatchingRequest(
        request: ConsumerMatchingConditionRequest,
    ): BaseResponse<ConsumerMatchingRequestResponse>
}
