package com.ssing.data.lesson.common.repository.impl

import com.ssing.core.network.socket.SocketState
import com.ssing.core.network.socket.lesson.LessonEnvelope
import com.ssing.data.lesson.common.remote.datasource.api.LessonSocketDataSource
import com.ssing.data.lesson.common.repository.api.LessonSocketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.json.JsonElement
import javax.inject.Inject

internal class LessonSocketRepositoryImpl @Inject constructor(
    private val dataSource: LessonSocketDataSource,
) : LessonSocketRepository {

    override val event: Flow<LessonEnvelope<JsonElement>>
        get() = dataSource.event

    override val socketState: StateFlow<SocketState>
        get() = dataSource.socketState

    override fun connect() {
        dataSource.connect()
    }

    override suspend fun disconnect() {
        dataSource.disconnect()
    }
}