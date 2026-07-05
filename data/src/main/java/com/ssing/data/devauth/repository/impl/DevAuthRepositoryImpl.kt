package com.ssing.data.devauth.repository.impl

import com.ssing.core.network.token.TokenAccessManager
import com.ssing.core.network.util.ApiResponseHandler
import com.ssing.core.network.util.suspendRunCatching
import com.ssing.data.devauth.remote.datasource.api.DevAuthRemoteDataSource
import com.ssing.data.devauth.remote.dto.TokenRequest
import com.ssing.data.devauth.repository.api.DevAuthRepository
import javax.inject.Inject

class DevAuthRepositoryImpl @Inject constructor(
    private val apiResponseHandler: ApiResponseHandler,
    private val tokenAccessManager: TokenAccessManager,
    private val remoteDataSource: DevAuthRemoteDataSource,
) : DevAuthRepository {
    override suspend fun getPersonaKeys(): Result<List<String>> =
        apiResponseHandler.safeApiCall {
            remoteDataSource.getPersonas()
        }.map { data -> data.personas.map { it.personaKey } }

    override suspend fun personaLogin(
        personaKey: String,
        autoCreate: Boolean,
    ): Result<Unit> = apiResponseHandler.safeApiCall {
        remoteDataSource.postToken(
            request = TokenRequest(
                personaKey = personaKey,
                autoCreate = autoCreate,
            )
        )
    }.fold(
        onSuccess = { response ->
            suspendRunCatching {
                tokenAccessManager.withLock {
                    setAccessToken(response.accessToken)
                    setRefreshToken(response.refreshToken)
                }
            }
        },
        onFailure = { throwable -> Result.failure(throwable) },
    )
}
