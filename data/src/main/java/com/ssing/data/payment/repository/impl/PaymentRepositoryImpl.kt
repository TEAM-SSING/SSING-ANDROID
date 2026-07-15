package com.ssing.data.payment.repository.impl

import com.ssing.core.network.util.ApiResponseHandler
import com.ssing.data.payment.model.PaymentSummary
import com.ssing.data.payment.remote.datasource.api.PaymentRemoteDataSource
import com.ssing.data.payment.remote.dto.response.PaymentResponse
import com.ssing.data.payment.repository.api.PaymentRepository
import javax.inject.Inject

internal class PaymentRepositoryImpl @Inject constructor(
    private val apiResponseHandler: ApiResponseHandler,
    private val dataSource: PaymentRemoteDataSource,
) : PaymentRepository {

    override suspend fun postPayment(
        matchingRequestId: Long,
    ): Result<PaymentSummary> =
        apiResponseHandler.safeApiCall {
            dataSource.postPaymentRequest(
                matchingRequestId = matchingRequestId,
            )
        }.map { response ->
            response.toModel()
        }

    private fun PaymentResponse.toModel(): PaymentSummary =
        PaymentSummary(
            matchingRequestId = matchingRequestId,
            matchingStatus = matchingStatus,
            paymentStatus = paymentStatus,
            groupId = groupId,
            groupStatus = groupStatus,
            paidCount = paidCount,
            requiredCount = requiredCount,
            lessonId = lessonId,
            expiresAt = expiresAt,
        )
}
