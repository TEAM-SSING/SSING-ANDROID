package com.ssing.core.network.session

import com.ssing.core.network.token.TokenAccessManager
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 세션 만료 이벤트를 앱 전역에 전파한다.
 *
 * 강제 로그아웃은 반드시 [forceLogout]을 통해야 한다.
 * clearTokens와 이벤트 발행이 항상 함께 실행되도록 보장한다.
 */
@Singleton
class AuthSessionManager @Inject constructor(
    private val tokenAccessManager: TokenAccessManager,
) {
    private val _sessionExpired = Channel<Unit>(Channel.CONFLATED)
    val sessionExpired: Flow<Unit> = _sessionExpired.receiveAsFlow()

    /**
     * 토큰 삭제 + 세션 만료 이벤트 발행.
     * TokenAccessManager의 withLock 밖에서 호출하는 경우에 사용한다.
     */
    suspend fun forceLogout() {
        tokenAccessManager.withLock { clearTokens() }
        _sessionExpired.trySend(Unit)
    }

    /**
     * 세션 만료 이벤트만 발행.
     * withLock 내부에서 이미 clearTokens를 수행한 후에만 호출해야 한다 (deadlock 방지).
     */
    internal fun notifySessionExpired() {
        _sessionExpired.trySend(Unit)
    }
}
