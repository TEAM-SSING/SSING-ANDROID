package com.ssing.data.consumerlesson.repository.api

import com.ssing.core.network.socket.SocketState
import com.ssing.data.consumerlesson.model.ConsumerLessonDetail
import com.ssing.data.consumerlesson.model.ConsumerLessonSocketEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ConsumerLessonDetailRepository {
    val socketEvents: Flow<ConsumerLessonSocketEvent>
    val socketState: StateFlow<SocketState>

    suspend fun fetchConsumerLessonDetail(lessonId: Long): Result<ConsumerLessonDetail>

    fun connectSocket()

    suspend fun disconnectSocket()
}