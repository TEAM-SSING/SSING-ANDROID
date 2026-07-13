package com.ssing.presentation.consumerlesson.util

internal fun displayLessonLevel(lessonLevel: String): String = when (lessonLevel) {
    "FIRST_TIME" -> "처음 타요"
    "BEGINNER" -> "1~5회 타봤어요"
    "INTERMEDIATE" -> "중급자예요"
    "CERTIFIED" -> "자격증이 있어요"
    else -> lessonLevel
}