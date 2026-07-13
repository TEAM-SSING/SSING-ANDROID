package com.ssing.presentation.consumerlesson.util

internal fun formatMinutesText(minutes: Int): String {
    val hours = minutes / 60
    val remain = minutes % 60
    return when {
        hours > 0 && remain > 0 -> "${hours}시간 ${remain}분"
        hours > 0 -> "${hours}시간"
        else -> "${remain}분"
    }
}