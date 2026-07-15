package com.ssing.data.notification.repository.impl

import com.ssing.core.network.util.ApiResponseHandler
import com.ssing.data.notification.model.NotificationItem
import com.ssing.data.notification.model.NotificationPage
import com.ssing.data.notification.model.NotificationType
import com.ssing.data.notification.remote.datasource.api.NotificationRemoteDataSource
import com.ssing.data.notification.remote.dto.response.NotificationResponse
import com.ssing.data.notification.remote.dto.response.NotificationsResponse
import com.ssing.data.notification.repository.api.NotificationRepository
import javax.inject.Inject

internal class NotificationRepositoryImpl @Inject constructor(
    private val apiResponseHandler: ApiResponseHandler,
    private val remoteDataSource: NotificationRemoteDataSource,
) : NotificationRepository {

    override suspend fun getNotifications(cursor: String?, size: Int): Result<NotificationPage> =
        apiResponseHandler.safeApiCall {
            remoteDataSource.getNotifications(cursor = cursor, size = size)
        }.map { it.toModel() }

    private fun NotificationsResponse.toModel() = NotificationPage(
        notifications = notifications.map { it.toModel() },
        nextCursor = nextCursor,
        hasNext = hasNext,
    )

    private fun NotificationResponse.toModel() = NotificationItem(
        id = notificationId,
        type = type.toNotificationType(),
        title = title,
        body = body,
        isRead = isRead,
        createdAt = createdAt,
    )

    private fun String.toNotificationType() = when (this) {
        "MATCHING_OFFER_RECEIVED" -> NotificationType.MATCHING_OFFER_RECEIVED
        "MATCHING_OFFER_CLOSED" -> NotificationType.MATCHING_OFFER_CLOSED
        "MATCHING_CONFIRMED" -> NotificationType.MATCHING_CONFIRMED
        else -> NotificationType.UNKNOWN
    }
}
