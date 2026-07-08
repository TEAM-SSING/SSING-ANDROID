package com.ssing.presentation.notification.model

internal enum class AlarmType(val label: String) {

    LESSON_ARRIVAL("씽 매칭 강습 도착"),

    LESSON_REJECTED("강습 거절"),

    LESSON_CONFIRMED("강습 확정"),
}
