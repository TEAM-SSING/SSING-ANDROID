package com.ssing.data.matching.instructormatching.repository.api

import com.ssing.data.matching.instructormatching.model.InstructorMatchingExposure
import com.ssing.data.matching.instructormatching.model.InstructorMatchingOffer

interface InstructorMatchingRepository {
    suspend fun fetchMatchingExposure(): Result<InstructorMatchingExposure>

    suspend fun fetchActiveOffer(): Result<InstructorMatchingOffer?>

    suspend fun startMatchingExposure(
        sport: String,
        lessonLevels: List<String>,
        availableDurationMinutes: List<Int>,
        maxHeadcount: Int,
        equipmentReady: Boolean,
    ): Result<Boolean>
}
