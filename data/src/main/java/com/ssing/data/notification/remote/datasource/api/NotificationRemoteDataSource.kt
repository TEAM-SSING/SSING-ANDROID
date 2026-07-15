package com.ssing.data.notification.remote.datasource.api

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.notification.remote.dto.response.NotificationsResponse

internal interface NotificationRemoteDataSource {
    suspend fun getNotifications(
        cursor: String?,
        size: Int,
    ): BaseResponse<NotificationsResponse>
}
