package com.ssing.data.matching.instructormatching.remote.datasource.api

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.matching.instructormatching.remote.dto.request.InstructorMatchingExposureStartRequest
import com.ssing.data.matching.instructormatching.remote.dto.response.InstructorMatchingExposureResponse
import com.ssing.data.matching.instructormatching.remote.dto.response.InstructorMatchingExposureStartResponse

internal interface InstructorMatchingRemoteDataSource {
    suspend fun getMatchingExposure(): BaseResponse<InstructorMatchingExposureResponse>

    suspend fun putMatchingExposure(
        request: InstructorMatchingExposureStartRequest,
    ): BaseResponse<InstructorMatchingExposureStartResponse>
}
