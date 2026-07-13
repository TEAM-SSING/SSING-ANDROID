package com.ssing.data.matching.common.remote.datasource.api

import com.ssing.core.network.socket.matching.MatchingEnvelope
import com.ssing.core.network.socket.SocketState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.json.JsonElement

internal interface MatchingSocketDataSource {
    val event: Flow<MatchingEnvelope<JsonElement>>
    val socketState: StateFlow<SocketState>
    fun connect()
    suspend fun disconnect()
}
