package com.ssing.data.auth.remote.datasource.api

interface LogoutDataSource {
    suspend fun logout(refreshToken: String)
}
