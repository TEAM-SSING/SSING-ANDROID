package com.ssing.core.ui.type

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

fun formatTime(isoDateTime: String): String {
    val dateTime = OffsetDateTime.parse(isoDateTime)
    return dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
}

fun formatDateTime(isoDateTime: String): String {
    val dateTime = OffsetDateTime.parse(isoDateTime)
    return dateTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"))
}

fun formatMinutesText(minutes: Int): String {
    val hours = minutes / 60
    val remain = minutes % 60
    return when {
        hours > 0 && remain > 0 -> "${hours}시간 ${remain}분"
        hours > 0 -> "${hours}시간"
        else -> "${remain}분"
    }
}

fun formatCountdown(totalSeconds: Int): String {
    val h = totalSeconds / 3600
    val m = (totalSeconds % 3600) / 60
    val s = totalSeconds % 60
    return "%d:%02d:%02d".format(h, m, s)
}
