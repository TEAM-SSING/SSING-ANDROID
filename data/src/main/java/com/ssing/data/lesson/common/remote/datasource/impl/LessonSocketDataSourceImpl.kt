package com.ssing.data.lesson.common.remote.datasource.impl

import com.ssing.core.network.socket.SocketManager
import com.ssing.core.network.socket.SocketState
import com.ssing.core.network.socket.lesson.LessonEnvelope
import com.ssing.data.lesson.common.remote.datasource.api.LessonSocketDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.json.JsonElement
import javax.inject.Inject

internal class LessonSocketDataSourceImpl @Inject constructor(
    private val socketManager: SocketManager<LessonEnvelope<JsonElement>>,
) : LessonSocketDataSource {
    override val event: Flow<LessonEnvelope<JsonElement>> = socketManager.event

    override val socketState: StateFlow<SocketState> = socketManager.socketState

    override fun connect() = socketManager.connect()

    override suspend fun disconnect() = socketManager.disconnect()
}
