package com.ssing.data.auth.remote.datasource.impl

import com.ssing.data.auth.remote.datasource.api.LogoutDataSource
import com.ssing.data.auth.remote.dto.request.LogoutRequest
import com.ssing.data.auth.remote.service.LogoutService
import javax.inject.Inject

internal class LogoutDataSourceImpl @Inject constructor(
    private val service: LogoutService,
) : LogoutDataSource {

    override suspend fun logout(refreshToken: String) =
        service.logout(LogoutRequest(refreshToken))
}
