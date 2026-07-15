package com.ssing.data.notification.repository.api

import com.ssing.data.notification.model.NotificationPage

interface NotificationRepository {

    suspend fun getNotifications(cursor: String?, size: Int): Result<NotificationPage>
}
