package com.ssing.data.payment.remote.datasource.api

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.payment.remote.dto.response.PaymentResponse

internal interface PaymentRemoteDataSource {
    suspend fun postPaymentRequest(
        matchingRequestId: Long,
    ): BaseResponse<PaymentResponse>
}