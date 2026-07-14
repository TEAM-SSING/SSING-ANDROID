package com.ssing.data.consumerlesson.repository.api

import com.ssing.data.consumerlesson.model.ConsumerLessonDetail
import com.ssing.data.consumerlesson.model.ConsumerLessonSocketEvent
import kotlinx.coroutines.flow.Flow

interface ConsumerLessonDetailRepository {
    val socketEvents: Flow<ConsumerLessonSocketEvent>

    suspend fun fetchConsumerLessonDetail(lessonId: Long): Result<ConsumerLessonDetail>

    fun connectSocket()

    suspend fun disconnectSocket()
}