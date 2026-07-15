package com.ssing.data.lesson.consumer.repository.api

import com.ssing.core.network.socket.SocketState
import com.ssing.data.consumerlesson.model.ConsumerLessonDetail
import com.ssing.data.lesson.consumer.model.ConsumerLessonDetail
import com.ssing.data.lesson.consumer.model.ConsumerLessonSocketEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ConsumerLessonRepository {
    val socketEvents: Flow<ConsumerLessonSocketEvent>
    val socketState: StateFlow<SocketState>
    fun connectSocket()
    suspend fun disconnectSocket()

    suspend fun fetchConsumerLessonDetail(lessonId: Long): Result<ConsumerLessonDetail>
}