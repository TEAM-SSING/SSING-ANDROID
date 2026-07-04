package com.ssing.data.repository

interface NotificationRepository {
    suspend fun saveNotificationToken(token: String): Result<Unit>
}