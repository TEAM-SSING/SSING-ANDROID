package com.ssing.data.payment.repository.api

import com.ssing.data.payment.model.PaymentSummary

interface PaymentRepository {
    suspend fun postPayment(matchingRequestId: Long, ): Result<PaymentSummary>
}
