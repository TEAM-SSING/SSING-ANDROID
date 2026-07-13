package com.ssing.data.matching.instructormatching.remote.datasource.api

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.matching.instructormatching.remote.dto.response.InstructorMatchingExposureResponse

internal interface InstructorMatchingRemoteDataSource {
    suspend fun getMatchingExposure(): BaseResponse<InstructorMatchingExposureResponse>
}