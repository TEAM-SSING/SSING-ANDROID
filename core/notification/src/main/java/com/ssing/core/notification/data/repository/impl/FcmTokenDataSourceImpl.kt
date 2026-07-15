package com.ssing.core.notification.data.repository.impl

import com.ssing.core.network.util.ApiResponseHandler
import com.ssing.core.notification.data.remote.datasource.FcmTokenDataSource
import com.ssing.core.notification.data.remote.dto.FcmTokenRegisterRequest
import com.ssing.core.notification.data.remote.dto.FcmTokenUnregisterRequest
import com.ssing.core.notification.data.repository.api.FcmTokenService
import javax.inject.Inject


class FcmTokenDataSourceImpl @Inject constructor(
    private val api: FcmTokenService,
    private val apiResponseHandler: ApiResponseHandler,
) : FcmTokenDataSource {

    override suspend fun registerFcmToken(request: FcmTokenRegisterRequest): Result<Unit> =
        apiResponseHandler.safeUnitApiCall { api.registerFcmToken(request) }

    override suspend fun unregisterFcmToken(request: FcmTokenUnregisterRequest): Result<Unit> =
        apiResponseHandler.safeUnitApiCall { api.unregisterFcmToken(request) }
}
