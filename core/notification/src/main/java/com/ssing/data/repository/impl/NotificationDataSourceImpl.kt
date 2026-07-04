package com.ssing.data.repository.impl

import com.ssing.core.network.util.ApiResponseHandler
import com.ssing.data.remote.datasource.NotificationDataSource
import com.ssing.data.remote.dto.NotificationRequestDto
import javax.inject.Inject

class NotificationDataSourceImpl @Inject constructor(
    private val apiResponseHandler: ApiResponseHandler,
    private val dataSource: NotificationDataSource,
) : NotificationDataSource {

    override suspend fun postNotificationToken(request: NotificationRequestDto): Result<Unit> =
        apiResponseHandler.safeUnitApiCall { dataSource.postNotificationToken(request) }

    override suspend fun saveNotificationToken(token: String): Result<Unit> =
        apiResponseHandler.safeUnitApiCall {
            dataSource.postNotificationToken(
                NotificationRequestDto(
                    token = token
                )
            )
        }
}
