package com.ssing.data.consumerlesson.repository.api

import com.ssing.data.consumerlesson.model.ConsumerLessonDetail

interface ConsumerLessonDetailRepository {
    suspend fun getConsumerLessonDetail(lessonId: Long): Result<ConsumerLessonDetail>
}