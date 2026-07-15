package com.ssing.data.notification.remote.datasource.impl

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.notification.remote.datasource.api.NotificationRemoteDataSource
import com.ssing.data.notification.remote.dto.response.NotificationsResponse
import com.ssing.data.notification.remote.service.NotificationService
import javax.inject.Inject

internal class NotificationRemoteDataSourceImpl @Inject constructor(
    private val notificationService: NotificationService,
) : NotificationRemoteDataSource {

    override suspend fun getNotifications(
        cursor: String?,
        size: Int,
    ): BaseResponse<NotificationsResponse> =
        notificationService.getNotifications(cursor = cursor, size = size)
}
