package com.ssing.data.repository.impl

import com.ssing.core.network.util.ApiResponseHandler
import com.ssing.data.remote.datasource.NotificationDataSource
import com.ssing.data.remote.dto.NotificationRequest
import com.ssing.data.repository.api.NotificationService
import javax.inject.Inject


class NotificationDataSourceImpl @Inject constructor(
    private val api: NotificationService,
    private val apiResponseHandler: ApiResponseHandler,
) : NotificationDataSource {

    override suspend fun postNotificationToken(request: NotificationRequest): Result<Unit> =
        apiResponseHandler.safeUnitApiCall { api.postNotificationToken(request) }

    override suspend fun deleteNotificationToken(token: String): Result<Unit> =
        apiResponseHandler.safeUnitApiCall { api.deleteNotificationToken(token) }
}
