package com.ssing.data.remote.datasource

import com.ssing.data.remote.dto.NotificationRequest


interface NotificationDataSource {
    suspend fun postNotificationToken(request: NotificationRequest): Result<Unit>

    suspend fun deleteNotificationToken(token: String): Result<Unit>
}