package com.ssing.data.dummy.repository.impl

import com.ssing.core.network.extension.mapApiError
import com.ssing.core.network.util.ApiResponseHandler
import com.ssing.data.dummy.exception.LoginException
import com.ssing.data.dummy.model.DummyInstructor
import com.ssing.data.dummy.remote.datasource.api.DummyDataSource
import com.ssing.data.dummy.remote.dto.GetInstructorResponse
import com.ssing.data.dummy.repository.api.DummyRepository
import javax.inject.Inject

class DummyRepositoryImpl @Inject constructor(
    private val apiResponseHandler: ApiResponseHandler,
    private val dummyDataSource: DummyDataSource,
) : DummyRepository {

    override suspend fun fetchInstructorList(): Result<List<DummyInstructor>> =
        apiResponseHandler.safeApiCall {
            dummyDataSource.getInstructorList()
        }.map { data -> data.map { it.toDummyInstructor() } }

    override suspend fun login(): Result<Unit> =
        apiResponseHandler.safeUnitApiCall {
            dummyDataSource.postLogin()
        }.mapApiError {
            when (it.serverCode) {
                "BLOCKED_USER" -> LoginException.BlockedUser(it.serverCode, it.message, it.requestId)
                else -> it
            }
        }

    private fun GetInstructorResponse.toDummyInstructor() = DummyInstructor(
        id = this.id ?: 0L,
        name = this.name.orEmpty(),
        rating = this.rating ?: 0f,
        career = this.career.orEmpty(),
        sport = this.sport.orEmpty(),
        level = this.level.orEmpty(),
        resort = this.resort.orEmpty(),
    )
}
