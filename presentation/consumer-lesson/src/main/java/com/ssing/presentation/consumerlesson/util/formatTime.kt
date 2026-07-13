package com.ssing.presentation.consumerlesson.util

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

internal fun formatTime(isoDateTime: String): String {
    val dateTime = OffsetDateTime.parse(isoDateTime)
    return dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
}