package com.ssing.presentation.notification.model

import androidx.compose.runtime.Immutable

@Immutable
internal data class AlarmUiModel(
    val id: Long,
    val type: AlarmType,
    val title: String,
    val content: String,
    val date: String,
    val isRead: Boolean = false,
    val isTargetAvailable: Boolean = true,
)
