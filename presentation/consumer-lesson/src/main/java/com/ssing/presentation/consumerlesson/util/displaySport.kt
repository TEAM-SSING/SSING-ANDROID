package com.ssing.presentation.consumerlesson.util

internal fun displaySport(sport: String): String = when (sport) {
    "SNOWBOARD" -> "스노보드"
    "SKI" -> "스키"
    else -> sport
}