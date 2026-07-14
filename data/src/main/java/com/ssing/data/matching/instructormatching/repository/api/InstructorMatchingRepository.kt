package com.ssing.data.matching.instructormatching.repository.api

import com.ssing.data.matching.instructormatching.model.InstructorMatchingExposure
import com.ssing.data.matching.instructormatching.model.InstructorMatchingOffers

interface InstructorMatchingRepository {
    suspend fun fetchMatchingExposure(): Result<InstructorMatchingExposure>

    suspend fun fetchMatchingOffers(
        page: Int? = null,
        size: Int? = null,
    ): Result<InstructorMatchingOffers>

    suspend fun startMatchingExposure(
        sport: String,
        lessonLevels: List<String>,
        availableDurationMinutes: List<Int>,
        maxHeadcount: Int,
        equipmentReady: Boolean,
    ): Result<Boolean>
}
