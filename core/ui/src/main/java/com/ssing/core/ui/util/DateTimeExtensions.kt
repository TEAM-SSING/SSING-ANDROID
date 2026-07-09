package com.ssing.core.ui.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun LocalDateTime.ssingDateFormatter(): String {
    return this.format(DateTimeFormatter.ofPattern("yyyy. MM. dd (E) HH:mm", Locale.KOREAN))
}