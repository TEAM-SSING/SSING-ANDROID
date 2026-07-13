package com.ssing.data.matching.instructormatching.repository.impl

import com.ssing.core.network.util.ApiResponseHandler
import com.ssing.data.matching.instructormatching.model.InstructorMatchingExposure
import com.ssing.data.matching.instructormatching.model.InstructorMatchingResort
import com.ssing.data.matching.instructormatching.remote.datasource.api.InstructorMatchingRemoteDataSource
import com.ssing.data.matching.instructormatching.remote.dto.response.InstructorMatchingExposureResponse
import com.ssing.data.matching.instructormatching.repository.api.InstructorMatchingRepository
import javax.inject.Inject

internal class InstructorMatchingRepositoryImpl @Inject constructor(
    private val apiResponseHandler: ApiResponseHandler,
    private val remoteDataSource: InstructorMatchingRemoteDataSource,
) : InstructorMatchingRepository {

    override suspend fun fetchMatchingExposure(): Result<InstructorMatchingExposure> =
        apiResponseHandler.safeApiCall {
            remoteDataSource.getMatchingExposure()
        }.map { it.toModel() }

    private fun InstructorMatchingExposureResponse.toModel(): InstructorMatchingExposure =
        InstructorMatchingExposure(
            resort = InstructorMatchingResort(
                code = this.resort.code,
                displayName = this.resort.displayName,
            ),
            availableSports = this.availableSports,
        )
}