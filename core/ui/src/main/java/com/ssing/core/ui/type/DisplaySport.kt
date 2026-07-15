package com.ssing.core.ui.type

enum class Sport(val display: String) {
    SNOWBOARD("스노보드"),
    SKI("스키");

    companion object {
        fun fromRaw(raw: String): Sport? = entries.find { it.name == raw }
    }
}

fun displaySport(sport: String): String = Sport.fromRaw(sport)?.display ?: sport