package com.ssing.core.notification.data.repository.impl

import com.ssing.core.network.util.ApiResponseHandler
import com.ssing.core.notification.data.remote.datasource.NotificationDataSource
import com.ssing.core.notification.data.remote.dto.FcmTokenRegisterRequest
import com.ssing.core.notification.data.remote.dto.FcmTokenUnregisterRequest
import com.ssing.core.notification.data.repository.api.NotificationService
import javax.inject.Inject


class NotificationDataSourceImpl @Inject constructor(
    private val api: NotificationService,
    private val apiResponseHandler: ApiResponseHandler,
) : NotificationDataSource {

    override suspend fun registerFcmToken(request: FcmTokenRegisterRequest): Result<Unit> =
        apiResponseHandler.safeUnitApiCall { api.registerFcmToken(request) }

    override suspend fun unregisterFcmToken(request: FcmTokenUnregisterRequest): Result<Unit> =
        apiResponseHandler.safeUnitApiCall { api.unregisterFcmToken(request) }
}
