package com.ssing.data.lessoncancel.model

data class LessonCancelModel (
    val lessonId: Long,
    val lessonStatus: String,
    val canceledAt: String,
)