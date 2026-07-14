package com.ssing.data.matching.instructormatching.repository.api

import com.ssing.data.matching.instructormatching.model.InstructorMatchingExposure

interface InstructorMatchingRepository {
    suspend fun fetchMatchingExposure(): Result<InstructorMatchingExposure>
}