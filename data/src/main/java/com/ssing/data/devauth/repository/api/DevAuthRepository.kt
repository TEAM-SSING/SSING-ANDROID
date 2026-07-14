package com.ssing.data.devauth.repository.api

interface DevAuthRepository {
    suspend fun getPersonaKeys(): Result<List<String>>

    suspend fun personaLogin(
        personaKey: String,
        autoCreate: Boolean,
    ): Result<Unit>
}
