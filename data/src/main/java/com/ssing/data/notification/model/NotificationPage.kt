package com.ssing.data.notification.model

data class NotificationPage(
    val notifications: List<NotificationItem>,
    val nextCursor: String?,
    val hasNext: Boolean,
)
