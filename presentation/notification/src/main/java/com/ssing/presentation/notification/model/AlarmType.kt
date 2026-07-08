package com.ssing.presentation.notification.model

/**
 * 알림 유형
 * @param label 카드에 표시되는 분류 라벨입니다.
 */
internal enum class AlarmType(val label: String) {

    /** 씽 매칭으로 새로운 강습이 도착한 알림 → 강습 도착 화면 */
    LESSON_ARRIVAL("씽 매칭 강습 도착"),

    /** 수락했던 강습이 거절된 알림 → 매칭 로딩중 화면 */
    LESSON_REJECTED("강습 거절"),

    /** 강습이 확정된 알림 → 강습전 화면 */
    LESSON_CONFIRMED("강습 확정"),
}
