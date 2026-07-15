package com.ssing.data.lesson.consumer.model

data class ConsumerLessonSocketEvent(
    val lessonId: Long,
    val lessonStatus: String,
)