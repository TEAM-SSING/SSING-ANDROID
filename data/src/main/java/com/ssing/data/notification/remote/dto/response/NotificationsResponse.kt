package com.ssing.data.notification.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class NotificationsResponse(
    @SerialName("notifications")
    val notifications: List<NotificationResponse>,
    @SerialName("nextCursor")
    val nextCursor: String? = null,
    @SerialName("hasNext")
    val hasNext: Boolean,
)

@Serializable
internal data class NotificationResponse(
    @SerialName("notificationId")
    val notificationId: Long,
    @SerialName("type")
    val type: String,
    @SerialName("title")
    val title: String,
    @SerialName("body")
    val body: String,
    @SerialName("isRead")
    val isRead: Boolean,
    @SerialName("createdAt")
    val createdAt: String,
)
