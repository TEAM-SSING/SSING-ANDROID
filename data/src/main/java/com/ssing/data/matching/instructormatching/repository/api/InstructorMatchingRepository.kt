package com.ssing.data.matching.instructormatching.repository.api

import com.ssing.data.matching.instructormatching.model.InstructorMatchingExposure
import com.ssing.data.matching.instructormatching.model.InstructorMatchingOffer
import com.ssing.data.matching.instructormatching.model.InstructorMatchingSocketEvent
import kotlinx.coroutines.flow.Flow

interface InstructorMatchingRepository {
    val socketEvents: Flow<InstructorMatchingSocketEvent>

    suspend fun fetchMatchingExposure(): Result<InstructorMatchingExposure>

    suspend fun fetchActiveOffer(): Result<InstructorMatchingOffer?>

    suspend fun startMatchingExposure(
        sport: String,
        lessonLevels: List<String>,
        availableDurationMinutes: List<Int>,
        maxHeadcount: Int,
        equipmentReady: Boolean,
    ): Result<Boolean>

    fun connectSocket()

    suspend fun disconnectSocket()
}
