package com.ssing.core.network.socket

import com.ssing.core.localstorage.datastore.LocalTokenDataSource
import com.ssing.core.network.BuildConfig
import com.ssing.core.network.util.suspendRunCatching
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import org.hildan.krossbow.stomp.StompClient
import org.hildan.krossbow.stomp.StompErrorFrameReceived
import org.hildan.krossbow.stomp.StompSession
import org.hildan.krossbow.stomp.headers.StompSubscribeHeaders

@OptIn(ExperimentalSerializationApi::class)
abstract class BaseSocketManager<T>(
    ioDispatcher: CoroutineDispatcher,
    private val client: StompClient,
    private val tokenDataSource: LocalTokenDataSource,
    private val json: Json,
    private val serializer: KSerializer<T>,
    private val endpoint: String,
) {
    abstract val destination: String

    private var session: StompSession? = null
    private var reissueAttempted = false

    private val _socketState: MutableStateFlow<SocketState> =
        MutableStateFlow(SocketState.Disconnected)
    val socketState: StateFlow<SocketState> = _socketState.asStateFlow()

    private val _event = MutableSharedFlow<T>(extraBufferCapacity = 64)
    val event: SharedFlow<T> = _event.asSharedFlow()

    private val scope = CoroutineScope(ioDispatcher + SupervisorJob())

    fun connect(): Job = scope.launch {
        try {
            _socketState.update { SocketState.Connecting }
            val accessToken = tokenDataSource.getAccessToken() ?: return@launch logout()

            session = client.connect(
                url = "${BuildConfig.SOCKET_BASE_URL}/$endpoint",
                customStompConnectHeaders = mapOf("Authorization" to "Bearer $accessToken")
            )

            reissueAttempted = false
            _socketState.update { SocketState.Connected }
            subscribe()
        } catch (e: StompErrorFrameReceived) {
            when (e.frame.bodyAsText) {
                UNAUTHENTICATED, AUTH_INVALID_TOKEN -> logout()
                AUTH_TOKEN_EXPIRED -> {
                    if (reissueAttempted) {
                        logout()
                    } else {
                        reissue()
                            .onSuccess {
                                reissueAttempted = true
                                connect()
                            }
                            .onFailure { throwable ->
                                _socketState.update { SocketState.Error(throwable) }
                            }
                    }
                }

                FORBIDDEN -> _socketState.update { SocketState.Forbidden }
                else -> _socketState.update { SocketState.Error(e) }
            }
        }
    }

    suspend fun disconnect() {
        session?.disconnect()
        session = null
    }

    private suspend fun subscribe() {
        val session = session ?: return _socketState.update {
            SocketState.Error(
                IllegalStateException("Session Not Found")
            )
        }

        session.subscribe(StompSubscribeHeaders(destination))
            .map { frame -> json.decodeFromString(serializer, frame.bodyAsText) }
            .catch { throwable -> _socketState.update { SocketState.Error(throwable) } }
            .collect { parsed -> _event.emit(parsed) }
    }

    private suspend fun reissue(): Result<Unit> = suspendRunCatching {
        // TODO: reissue API 호출
    }

    private fun logout() {}

    private companion object {
        const val UNAUTHENTICATED = "UNAUTHENTICATED"
        const val AUTH_INVALID_TOKEN = "AUTH_INVALID_TOKEN"
        const val AUTH_TOKEN_EXPIRED = "AUTH_TOKEN_EXPIRED"
        const val FORBIDDEN = "FORBIDDEN"
    }
}

sealed interface SocketState {
    data object Connected : SocketState
    data object Connecting : SocketState
    data object Disconnected : SocketState
    data object Forbidden : SocketState
    data class Error(val throwable: Throwable) : SocketState
}
