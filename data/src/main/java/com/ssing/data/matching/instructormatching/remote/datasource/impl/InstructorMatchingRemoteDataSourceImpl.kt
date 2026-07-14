package com.ssing.data.matching.instructormatching.remote.datasource.impl

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.matching.instructormatching.remote.datasource.api.InstructorMatchingRemoteDataSource
import com.ssing.data.matching.instructormatching.remote.dto.request.InstructorMatchingExposureStartRequest
import com.ssing.data.matching.instructormatching.remote.dto.response.InstructorMatchingExposureResponse
import com.ssing.data.matching.instructormatching.remote.dto.response.InstructorMatchingExposureStartResponse
import com.ssing.data.matching.instructormatching.remote.service.InstructorMatchingService
import javax.inject.Inject

internal class InstructorMatchingRemoteDataSourceImpl @Inject constructor(
    private val instructorMatchingService: InstructorMatchingService,
) : InstructorMatchingRemoteDataSource {

    override suspend fun getMatchingExposure(): BaseResponse<InstructorMatchingExposureResponse> =
        instructorMatchingService.getMatchingExposure()

    override suspend fun putMatchingExposure(
        request: InstructorMatchingExposureStartRequest,
    ): BaseResponse<InstructorMatchingExposureStartResponse> =
        instructorMatchingService.putMatchingExposure(request)
}
