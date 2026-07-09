package com.ssing.data.auth.repository.api

interface LogoutRepository {
    suspend fun logout(): Result<Unit>
}
