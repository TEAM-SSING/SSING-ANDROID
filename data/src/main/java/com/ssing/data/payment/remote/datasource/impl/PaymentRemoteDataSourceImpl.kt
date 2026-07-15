package com.ssing.data.payment.remote.datasource.impl

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.payment.remote.datasource.api.PaymentRemoteDataSource
import com.ssing.data.payment.remote.dto.response.PaymentResponse
import com.ssing.data.payment.remote.service.PaymentService
import javax.inject.Inject

internal class PaymentRemoteDataSourceImpl @Inject constructor(
    private val paymentService: PaymentService,
) : PaymentRemoteDataSource {

    override suspend fun postPaymentRequest(
        matchingRequestId: Long,
    ): BaseResponse<PaymentResponse> =
        paymentService.postPaymentService(
            matchingRequestId = matchingRequestId,
        )
}
