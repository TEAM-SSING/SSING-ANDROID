package com.ssing.presentation.consumerlesson.util

internal fun displayGender(gender: String): String = when (gender) {
    "MALE" -> "남"
    "FEMALE" -> "여"
    else -> gender
}