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
import timber.log.Timber
import kotlin.math.pow

/**
 * STOMP 프로토콜 기반 웹소켓 연결을 추상화한 베이스 클래스.
 *
 * 연결/재연결/인증(토큰 만료 시 재발급)을 내부에서 처리하므로
 * 서브클래스는 [destination]과 [send] 호출만 구현하면 된다.
 *
 * - 연결 상태는 [socketState]로 구독
 * - 수신 이벤트는 [event]로 구독
 * - 서버에 의한 연결 종료 시 최대 5회 지수 백오프로 자동 재연결
 *
 * @param T 수신 이벤트 타입
 * @param ioDispatcher 소켓 통신에 사용할 디스패처
 * @param client krossbow [StompClient] 인스턴스
 * @param tokenDataSource 액세스 토큰 조회 소스
 * @param json JSON 직렬화 인스턴스
 * @param serializer 수신 메시지 역직렬화에 사용할 [KSerializer]
 * @param endpoint 연결할 웹소켓 엔드포인트 경로 (예: "/ws/matching")
 */
@OptIn(ExperimentalSerializationApi::class)
abstract class BaseSocketManager<T>(
    ioDispatcher: CoroutineDispatcher,
    private val client: StompClient,
    private val tokenDataSource: LocalTokenDataSource,
    private val json: Json,
    private val serializer: KSerializer<T>,
) {
    /**
     * WebSocket 업그레이드 요청 경로
     */
    open val endpoint: String = "ws/realtime"

    /**
     * STOMP 구독 destination
     * 예: `/user/queue/matching` (즉시 매칭), `/user/queue/lesson` (강습)
     */
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
        if (_socketState.value == SocketState.Connecting || _socketState.value == SocketState.Connected) {
            Timber.d("🐮 connect() 호출됐지만 이미 연결 중 또는 연결됨 (state: ${_socketState.value})")
            return
        }

        Timber.d("🐮 connect() - 연결 시작 (endpoint: $endpoint)")
        connectJob?.cancel()
        isIntentionalDisconnect = false
        retryCount = 0

        connectJob = scope.launch { executeConnect() }
    }

    private suspend fun executeConnect() {
        try {
            _socketState.update { SocketState.Connecting }
            val accessToken = tokenDataSource.getAccessToken() ?: return logout()

            Timber.d("🐮 소켓 연결 시도 중 (url: ${BuildConfig.SOCKET_BASE_URL}/$endpoint)")
            session = client.connect(
                url = "${BuildConfig.SOCKET_BASE_URL}/$endpoint",
                customStompConnectHeaders = mapOf("Authorization" to "Bearer $accessToken")
            )

            reissueAttempted = false
            Timber.d("🐮 소켓 연결 성공")
            _socketState.update { SocketState.Connected }
            subscribe()
        } catch (s: StompErrorFrameReceived) {
            Timber.w("🐮 STOMP 에러 프레임 수신: ${s.frame.bodyAsText}")
            when (s.frame.bodyAsText) {
                UNAUTHENTICATED, AUTH_INVALID_TOKEN -> logout()
                AUTH_TOKEN_EXPIRED -> handleTokenExpired()
                FORBIDDEN -> {
                    Timber.w("🐮 접근 권한 없음 (FORBIDDEN)")
                    _socketState.update { SocketState.Forbidden }
                }

                else -> {
                    Timber.e("🐮 알 수 없는 STOMP 에러: ${s.frame.bodyAsText}")
                    _socketState.update { SocketState.Error(s) }
                }
            }
        } catch (c: CancellationException) {
            throw c
        } catch (e: Exception) {
            Timber.e(e, "🐮 소켓 연결 중 예외 발생")
            if (!isIntentionalDisconnect) {
                retryConnect()
            } else {
                _socketState.update { SocketState.Error(e) }
            }
        }
    }

    suspend fun disconnect() {
        Timber.d("🐮 disconnect() - 연결 해제")
        isIntentionalDisconnect = true
        connectJob?.cancel()
        connectJob = null
        session?.disconnect()
        session = null
        reissueAttempted = false
        _socketState.update { SocketState.Disconnected }
    }

    private suspend fun subscribe() {
        val currentSession = session ?: run {
            Timber.e("🐮 구독 실패 - 세션 없음")
            return _socketState.update { SocketState.Error(IllegalStateException("Session Not Found")) }
        }

        Timber.d("🐮 구독 시작 (destination: $destination)")
        currentSession.subscribe(StompSubscribeHeaders(destination))
            .map { frame -> json.decodeFromString(serializer, frame.bodyAsText) }
            .catch { throwable ->
                Timber.e(throwable, "🐮 구독 중 에러 발생")
                session = null
                _socketState.update { SocketState.Error(throwable) }
            }
            .collect { parsed -> _event.emit(parsed) }

        session = null
        if (!isIntentionalDisconnect && _socketState.value !is SocketState.Error) {
            Timber.w("🐮 서버에 의해 구독 종료 - 재연결 시도")
            _socketState.update { SocketState.Disconnected }
            retryConnect()
        }
    }

    private suspend fun retryConnect() {
        if (retryCount >= 5) {
            Timber.e("🐮 최대 재연결 횟수(5회) 초과 - 재연결 중단")
            _socketState.update {
                SocketState.Error(IllegalStateException("최대 재연결 시도 횟수(5회)를 초과"))
            }
            return
        }

        retryCount++

        val delayMillis = (1000L * 2.0.pow(retryCount))
            .toLong()
            .coerceAtMost(10000L)

        Timber.w("🐮 재연결 시도 중 (${retryCount}회차, ${delayMillis}ms 후 시도)")
        delay(delayMillis)

        executeConnect()
    }

    protected suspend fun <V> send(
        destination: String,
        body: V,
        serializer: KSerializer<V>,
    ): Result<Unit> {
        val currentSession = session ?: run {
            Timber.e("🐮 send() 실패 - 세션 없음 (destination: $destination)")
            _socketState.update { SocketState.Error(IllegalStateException("Session Not Found")) }
            return Result.failure(IllegalStateException("Session Not Found"))
        }

        return suspendRunCatching {
            val encodedBody = json.encodeToString(serializer, body)
            currentSession.send(
                StompSendHeaders(destination = destination),
                FrameBody.Text(encodedBody)
            )
            Unit
        }.onFailure { throwable ->
            Timber.e(throwable, "🐮 send() 실패 (destination: $destination)")
        }
    }

    private suspend fun handleTokenExpired() {
        if (reissueAttempted) {
            Timber.w("🐮 토큰 재발급 후에도 만료 - 로그아웃 처리")
            logout()
        } else {
            Timber.d("🐮 액세스 토큰 만료 - 재발급 시도")
            reissue()
                .onSuccess {
                    if (_socketState.value == SocketState.Disconnected) return

                    Timber.d("🐮 토큰 재발급 성공 - 재연결 시도")
                    reissueAttempted = true
                    connectJob = scope.launch { executeConnect() }
                }
                .onFailure { throwable ->
                    Timber.e(throwable, "🐮 토큰 재발급 실패")
                    _socketState.update { SocketState.Error(throwable) }
                }
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
