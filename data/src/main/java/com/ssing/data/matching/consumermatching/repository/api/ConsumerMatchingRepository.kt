package com.ssing.data.matching.consumermatching.repository.api

import com.ssing.core.network.socket.SocketState
import com.ssing.data.matching.consumermatching.event.ConsumerMatchingEvent
import com.ssing.data.matching.consumermatching.model.ConsumerMatchingRequestResult
import com.ssing.data.matching.consumermatching.model.ConsumerMatchingParticipant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ConsumerMatchingRepository {
    val event: Flow<ConsumerMatchingEvent>

    val socketState: StateFlow<SocketState>

    fun connect()

    suspend fun disconnect()

    suspend fun requestMatching(
        resort: String,
        sport: String,
        lessonLevel: String,
        requestedDurationMinutes: List<Int>,
        participants: List<ConsumerMatchingParticipant>,
        equipmentReady: Boolean,
    ): Result<ConsumerMatchingRequestResult>

    suspend fun cancelMatching(
        matchingRequestId: Long,
    ): Result<Unit>

    suspend fun confirmMatching(
        matchingRequestId: Long,
        decision: String,
    ): Result<Unit>

    suspend fun getMatchingActive(): Result<Long?>
}
