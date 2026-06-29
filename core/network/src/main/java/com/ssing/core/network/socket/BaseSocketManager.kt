package com.ssing.core.network.socket

import com.ssing.core.localstorage.datastore.LocalTokenDataSource
import com.ssing.core.network.BuildConfig
import com.ssing.core.network.util.suspendRunCatching
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
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
import kotlin.math.pow

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

    @Volatile
    private var session: StompSession? = null

    @Volatile
    private var reissueAttempted = false

    private val _socketState: MutableStateFlow<SocketState> =
        MutableStateFlow(SocketState.Disconnected)
    val socketState: StateFlow<SocketState> = _socketState.asStateFlow()

    private val _event = MutableSharedFlow<T>(extraBufferCapacity = 64)
    val event: SharedFlow<T> = _event.asSharedFlow()

    private val scope = CoroutineScope(ioDispatcher + SupervisorJob())

    @Volatile
    private var connectJob: Job? = null

    @Volatile
    private var isIntentionalDisconnect = true

    private var retryCount: Int = 0

    fun connect() {
        if (_socketState.value == SocketState.Connecting || _socketState.value == SocketState.Connected) return

        connectJob?.cancel()
        isIntentionalDisconnect = false

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
            retryCount = 0
            _socketState.update { SocketState.Connected }
            subscribe()
        } catch (s: StompErrorFrameReceived) {
            when (s.frame.bodyAsText) {
                UNAUTHENTICATED, AUTH_INVALID_TOKEN -> logout()
                AUTH_TOKEN_EXPIRED -> {
                    if (reissueAttempted) {
                        logout()
                    } else {
                        reissue()
                            .onSuccess {
                                if (_socketState.value == SocketState.Disconnected) return

                                reissueAttempted = true
                                connectJob = scope.launch { executeConnect() }
                            }
                            .onFailure { throwable ->
                                _socketState.update { SocketState.Error(throwable) }
                            }
                    }
                }

                FORBIDDEN -> _socketState.update { SocketState.Forbidden }
                else -> _socketState.update { SocketState.Error(s) }
            }
        } catch (c: CancellationException) {
            throw c
        } catch (e: Exception) {
            _socketState.update { SocketState.Error(e) }
        }
    }

    suspend fun disconnect() {
        isIntentionalDisconnect = true
        connectJob?.cancel()
        connectJob = null
        session?.disconnect()
        session = null
        reissueAttempted = false
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

        session = null
        if (!isIntentionalDisconnect && _socketState.value !is SocketState.Error) {
            _socketState.update { SocketState.Disconnected }
            retryConnect()
        }
    }

    private suspend fun retryConnect() {
        if (retryCount >= 5) {
            _socketState.update {
                SocketState.Error(IllegalStateException("최대 재연결 시도 횟수(5회)를 초과"))
            }
            return
        }

        retryCount++

        val delayMillis = (1000L * 2.0.pow(retryCount))
            .toLong()
            .coerceAtMost(10000L)

        delay(delayMillis)

        executeConnect()
    }

    protected suspend fun <V> send(
        destination: String,
        body: V,
        serializer: KSerializer<V>,
    ): Result<Unit> {
        val currentSession = session ?: run {
            _socketState.update { SocketState.Error(IllegalStateException("Session Not Found")) }
            return Result.failure(IllegalStateException("Session Not Found"))
        }

        return suspendRunCatching {
            val encodedBody = json.encodeToString(serializer, body)
            currentSession.send(
                StompSendHeaders(destination = destination),
                FrameBody.Text(encodedBody)
            )
        }
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
