package com.ssing.data.notification.model

data class NotificationItem(
    val id: Long,
    val type: NotificationType,
    val title: String,
    val body: String,
    val isRead: Boolean,
    val createdAt: String,
)
