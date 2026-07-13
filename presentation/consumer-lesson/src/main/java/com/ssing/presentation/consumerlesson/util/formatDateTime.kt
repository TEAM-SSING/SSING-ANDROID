package com.ssing.presentation.consumerlesson.util

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

internal fun formatDateTime(isoDateTime: String): String {
    val dateTime = OffsetDateTime.parse(isoDateTime)
    return dateTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"))
}