package com.ssing.core.ui.type

enum class LessonLevel(val display: String) {
    FIRST_TIME("처음 타요"),
    BEGINNER("1~5회 타봤어요"),
    INTERMEDIATE("중급자예요"),
    CERTIFIED("자격증이 있어요");

    companion object {
        fun fromRaw(raw: String): LessonLevel? = entries.find { it.name == raw }
    }
}

fun displayLessonLevel(lessonLevel: String): String = LessonLevel.fromRaw(lessonLevel)?.display ?: lessonLevel