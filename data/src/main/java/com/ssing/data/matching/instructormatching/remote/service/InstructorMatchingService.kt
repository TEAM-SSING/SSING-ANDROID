package com.ssing.data.matching.instructormatching.remote.service

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.matching.instructormatching.remote.dto.request.InstructorMatchingExposureStartRequest
import com.ssing.data.matching.instructormatching.remote.dto.response.InstructorMatchingExposureResponse
import com.ssing.data.matching.instructormatching.remote.dto.response.InstructorMatchingExposureStartResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

internal interface InstructorMatchingService {
    @GET("/api/v1/instructor/matching-exposure")
    suspend fun getMatchingExposure(): BaseResponse<InstructorMatchingExposureResponse>

    @PUT("/api/v1/instructor/matching-exposure")
    suspend fun putMatchingExposure(
        @Body request: InstructorMatchingExposureStartRequest,
    ): BaseResponse<InstructorMatchingExposureStartResponse>
}
