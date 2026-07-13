package com.ssing.data.matching.common.remote.datasource.impl

import com.ssing.core.network.socket.SocketManager
import com.ssing.core.network.socket.SocketState
import com.ssing.core.network.socket.matching.MatchingEnvelope
import com.ssing.data.matching.common.remote.datasource.api.MatchingSocketDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.json.JsonElement
import javax.inject.Inject

internal class MatchingSocketDataSourceImpl @Inject constructor(
    private val socketManager: SocketManager<MatchingEnvelope<JsonElement>>,
) : MatchingSocketDataSource {
    override val event: Flow<MatchingEnvelope<JsonElement>> = socketManager.event
    override val socketState: StateFlow<SocketState> = socketManager.socketState
    override fun connect() = socketManager.connect()
    override suspend fun disconnect() = socketManager.disconnect()
}
