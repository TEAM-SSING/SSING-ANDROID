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
import org.hildan.krossbow.stomp.frame.FrameBody
import org.hildan.krossbow.stomp.headers.StompSendHeaders
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
    private var connectJob: Job? = null

    fun connect() {
        if (_socketState.value == SocketState.Connecting || _socketState.value == SocketState.Connected) return

        connectJob?.cancel()

        connectJob = scope.launch { executeConnect() }
    }

    private suspend fun executeConnect() {
        try {
            _socketState.update { SocketState.Connecting }
            val accessToken = tokenDataSource.getAccessToken() ?: return logout()

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
                                connectJob = scope.launch { executeConnect() }
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
        connectJob?.cancel()
        connectJob = null
        session?.disconnect()
        session = null
        _socketState.update { SocketState.Disconnected }
    }

    private suspend fun subscribe() {
        val currentSession = session ?: return _socketState.update {
            SocketState.Error(
                IllegalStateException("Session Not Found")
            )
        }

        currentSession.subscribe(StompSubscribeHeaders(destination))
            .map { frame -> json.decodeFromString(serializer, frame.bodyAsText) }
            .catch { throwable ->
                session = null
                _socketState.update {
                    SocketState.Error(throwable)
                }
            }
            .collect { parsed -> _event.emit(parsed) }
    }

    protected suspend fun <V> send(destination: String, body: V, serializer: KSerializer<V>) {
        val currentSession = session ?: return _socketState.update {
            SocketState.Error(
                IllegalStateException("Session Not Found")
            )
        }

        val encodedBody = json.encodeToString(serializer, body)
        currentSession.send(
            StompSendHeaders(destination = destination),
            FrameBody.Text(encodedBody)
        )
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
