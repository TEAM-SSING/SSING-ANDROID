package com.ssing.core.notification

interface NotificationRepository {
    suspend fun saveNotificationToken(token: String): Result<Unit>
}