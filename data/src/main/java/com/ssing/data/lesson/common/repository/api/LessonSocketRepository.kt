package com.ssing.data.lesson.common.repository.api

import com.ssing.core.network.socket.SocketState
import com.ssing.core.network.socket.lesson.LessonEnvelope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.json.JsonElement

interface LessonSocketRepository {
    val event: Flow<LessonEnvelope<JsonElement>>
    val socketState: StateFlow<SocketState>

    fun connect()

    suspend fun disconnect()
}