package com.ssing.data.matching.instructormatching.repository.impl

import com.ssing.core.network.util.ApiResponseHandler
import com.ssing.data.matching.instructormatching.model.InstructorMatchingExposure
import com.ssing.data.matching.instructormatching.model.InstructorMatchingResort
import com.ssing.data.matching.instructormatching.remote.datasource.api.InstructorMatchingRemoteDataSource
import com.ssing.data.matching.instructormatching.remote.dto.request.InstructorMatchingExposureStartRequest
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

    override suspend fun startMatchingExposure(
        sport: String,
        lessonLevels: List<String>,
        availableDurationMinutes: List<Int>,
        maxHeadcount: Int,
        equipmentReady: Boolean,
    ): Result<Boolean> =
        apiResponseHandler.safeApiCall {
            remoteDataSource.putMatchingExposure(
                request = InstructorMatchingExposureStartRequest(
                    sport = sport,
                    lessonLevels = lessonLevels,
                    availableDurationMinutes = availableDurationMinutes,
                    maxHeadcount = maxHeadcount,
                    equipmentReady = equipmentReady,
                ),
            )
        }.map { it.isExposed }

    private fun InstructorMatchingExposureResponse.toModel(): InstructorMatchingExposure =
        InstructorMatchingExposure(
            resort = InstructorMatchingResort(
                code = this.resort.code,
                displayName = this.resort.displayName,
            ),
            availableSports = this.availableSports,
        )
}
