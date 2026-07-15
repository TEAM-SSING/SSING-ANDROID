package com.ssing.data.matching.instructormatching.repository.api

import com.ssing.core.network.socket.SocketState
import com.ssing.data.matching.instructormatching.event.InstructorMatchingEvent
import com.ssing.data.matching.instructormatching.model.InstructorMatchingExposure
import com.ssing.data.matching.instructormatching.model.InstructorMatchingOffer
import com.ssing.data.matching.instructormatching.model.InstructorMatchingOfferDecision
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface InstructorMatchingRepository {
    val event: Flow<InstructorMatchingEvent>

    val socketState: StateFlow<SocketState>

    suspend fun fetchMatchingExposure(): Result<InstructorMatchingExposure>

    suspend fun fetchActiveOffer(): Result<InstructorMatchingOffer?>

    suspend fun startMatchingExposure(
        sport: String,
        lessonLevels: List<String>,
        availableDurationMinutes: List<Int>,
        maxHeadcount: Int,
        equipmentReady: Boolean,
    ): Result<Boolean>

    suspend fun cancelMatchingExposure(): Result<Boolean>

    suspend fun respondMatchingOffer(
        offerId: Long,
        decision: String,
    ): Result<InstructorMatchingOfferDecision>

    fun connectSocket()

    suspend fun disconnectSocket()
}
