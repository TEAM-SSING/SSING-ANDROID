package com.ssing.core.ui.type

enum class Gender(val display: String) {
    MALE("남"),
    FEMALE("여");

    companion object {
        fun fromRaw(raw: String): Gender? = entries.find { it.name == raw }
    }
}

fun displayGender(gender: String): String = Gender.fromRaw(gender)?.display ?: gender

