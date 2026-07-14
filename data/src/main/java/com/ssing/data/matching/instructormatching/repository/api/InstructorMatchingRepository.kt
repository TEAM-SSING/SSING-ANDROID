package com.ssing.data.matching.instructormatching.repository.api

import com.ssing.data.matching.instructormatching.model.InstructorMatchingExposure

interface InstructorMatchingRepository {
    suspend fun fetchMatchingExposure(): Result<InstructorMatchingExposure>

    suspend fun startMatchingExposure(
        sport: String,
        lessonLevels: List<String>,
        availableDurationMinutes: List<Int>,
        maxHeadcount: Int,
        equipmentReady: Boolean,
    ): Result<Boolean>
}