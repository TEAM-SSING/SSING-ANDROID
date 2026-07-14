package com.ssing.data.matching.instructormatching.remote.datasource.api

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.matching.instructormatching.remote.dto.response.InstructorMatchingExposureResponse
import com.ssing.data.matching.instructormatching.remote.dto.response.InstructorMatchingOffersResponse

internal interface InstructorMatchingRemoteDataSource {
    suspend fun getMatchingExposure(): BaseResponse<InstructorMatchingExposureResponse>

    suspend fun getMatchingOffers(
        page: Int? = null,
        size: Int? = null,
    ): BaseResponse<InstructorMatchingOffersResponse>
}