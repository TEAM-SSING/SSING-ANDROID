package com.ssing.data.payment.remote.service

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.payment.remote.dto.request.PaymentRequest
import com.ssing.data.payment.remote.dto.response.PaymentResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

internal interface PaymentService {
    @POST("/api/v1/consumer/matching-requests/{matchingRequestId}/payment")
    suspend fun postPaymentService(
        @Path("matchingRequestId") matchingRequestId: Long,
        @Body request: PaymentRequest,
    ): BaseResponse<PaymentResponse>
}
