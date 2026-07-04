package com.ssing.data.remote.datasource

import com.ssing.data.remote.dto.NotificationRequestDto

interface NotificationDataSource {
    suspend fun postNotificationToken(request: NotificationRequestDto): Result<Unit>

    suspend fun deleteNotificationToken(token: String): Result<Unit>
}