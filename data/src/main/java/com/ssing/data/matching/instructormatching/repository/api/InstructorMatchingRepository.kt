package com.ssing.data.matching.instructormatching.repository.api

import com.ssing.data.matching.instructormatching.model.InstructorMatchingExposure
import com.ssing.data.matching.instructormatching.model.InstructorMatchingOffers

interface InstructorMatchingRepository {
    suspend fun fetchMatchingExposure(): Result<InstructorMatchingExposure>

    suspend fun fetchMatchingOffers(
        page: Int? = null,
        size: Int? = null,
    ): Result<InstructorMatchingOffers>
}