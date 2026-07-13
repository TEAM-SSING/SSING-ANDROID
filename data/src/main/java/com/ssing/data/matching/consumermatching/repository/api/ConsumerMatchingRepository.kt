package com.ssing.data.matching.consumermatching.repository.api

import com.ssing.data.consumermatching.model.ConsumerMatchingRequestResult
import com.ssing.data.consumermatching.model.ConsumerMatchingParticipant

interface ConsumerMatchingRepository {
    suspend fun requestMatching(
        resort: String,
        sport: String,
        lessonLevel: String,
        requestedDurationMinutes: List<Int>,
        participants: List<ConsumerMatchingParticipant>,
        equipmentReady: Boolean,
    ): Result<ConsumerMatchingRequestResult>
}
