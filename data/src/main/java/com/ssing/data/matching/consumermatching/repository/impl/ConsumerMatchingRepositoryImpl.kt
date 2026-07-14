package com.ssing.data.matching.consumermatching.repository.impl

import com.ssing.core.network.util.ApiResponseHandler
import com.ssing.data.matching.consumermatching.model.ConsumerMatchingParticipant
import com.ssing.data.matching.consumermatching.model.ConsumerMatchingRequestResult
import com.ssing.data.matching.consumermatching.remote.datasource.api.ConsumerMatchingRemoteDataSource
import com.ssing.data.matching.consumermatching.remote.dto.request.ConsumerMatchingConditionRequest
import com.ssing.data.matching.consumermatching.remote.dto.request.ConsumerMatchingParticipantRequest
import com.ssing.data.matching.consumermatching.remote.dto.response.ConsumerMatchingRequestResponse
import com.ssing.data.matching.consumermatching.repository.api.ConsumerMatchingRepository
import javax.inject.Inject

internal class ConsumerMatchingRepositoryImpl @Inject constructor(
    private val apiResponseHandler: ApiResponseHandler,
    private val remoteDataSource: ConsumerMatchingRemoteDataSource,
) : ConsumerMatchingRepository {
    override suspend fun requestMatching(
        resort: String,
        sport: String,
        lessonLevel: String,
        requestedDurationMinutes: List<Int>,
        participants: List<ConsumerMatchingParticipant>,
        equipmentReady: Boolean,
    ): Result<ConsumerMatchingRequestResult> = apiResponseHandler.safeApiCall {
        remoteDataSource.postMatchingRequest(
            request = ConsumerMatchingConditionRequest(
                resort = resort,
                sport = sport,
                lessonLevel = lessonLevel,
                requestedDurationMinutes = requestedDurationMinutes,
                consumerMatchingParticipantRequests = participants.map { it.toRequest() },
                equipmentReady = equipmentReady,
            )
        )
    }.map { it.toModel() }

    override suspend fun cancelMathcing(matchingRequestId: Long): Result<Unit> =
        apiResponseHandler.safeApiCall {
            remoteDataSource.postMatchingCancellation(matchingRequestId)
        }.map { }

    private fun ConsumerMatchingParticipant.toRequest(): ConsumerMatchingParticipantRequest =
        ConsumerMatchingParticipantRequest(
            age = this.age,
            gender = this.gender,
        )

    private fun ConsumerMatchingRequestResponse.toModel(): ConsumerMatchingRequestResult =
        ConsumerMatchingRequestResult(
            matchingRequestId = this.matchingRequestId,
            matchingStatus = this.matchingStatus,
            requestStatus = this.requestStatus,
            expiresAt = this.expiresAt,
            requestStatusReason = this.requestStatusReason,
        )
}
