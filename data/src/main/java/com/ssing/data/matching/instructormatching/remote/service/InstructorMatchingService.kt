package com.ssing.data.matching.instructormatching.remote.service

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.matching.instructormatching.remote.dto.response.InstructorMatchingExposureResponse
import retrofit2.http.GET

internal interface InstructorMatchingService {
    @GET("/api/v1/instructor/matching-exposure")
    suspend fun getMatchingExposure(): BaseResponse<InstructorMatchingExposureResponse>
}