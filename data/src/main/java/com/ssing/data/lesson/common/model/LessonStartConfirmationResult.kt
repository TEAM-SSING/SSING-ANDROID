package com.ssing.data.lesson.common.model

sealed interface LessonStartConfirmationResult {
    val lessonId: Long

    data class Pending(
        override val lessonId: Long,
        val confirmedCount: Int,
        val requiredCount: Int,
        val currentActorConfirmed: Boolean,
        val instructorConfirmed: Boolean,
    ) : LessonStartConfirmationResult

    data class Started(
        override val lessonId: Long,
        val startedAt: String,
    ) : LessonStartConfirmationResult
}