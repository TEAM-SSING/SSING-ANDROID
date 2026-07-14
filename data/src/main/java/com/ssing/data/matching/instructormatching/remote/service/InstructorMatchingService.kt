package com.ssing.data.matching.instructormatching.remote.service

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.matching.instructormatching.remote.dto.response.InstructorMatchingExposureResponse
import com.ssing.data.matching.instructormatching.remote.dto.response.InstructorMatchingOffersResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface InstructorMatchingService {
    @GET("/api/v1/instructor/matching-exposure")
    suspend fun getMatchingExposure(): BaseResponse<InstructorMatchingExposureResponse>

    @GET("/api/v1/instructor/matching-offers")
    suspend fun getMatchingOffers(
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
    ): BaseResponse<InstructorMatchingOffersResponse>
}