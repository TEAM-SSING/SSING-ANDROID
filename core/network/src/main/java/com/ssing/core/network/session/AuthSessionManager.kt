package com.ssing.core.network.session

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 세션 만료(Refresh Token 만료 등) 이벤트를 앱 전역에 전파한다.
 *
 * [sessionExpired]를 MainActivity(또는 앱 레벨 ViewModel)에서 collect해서
 * 로그인 화면으로 이동시키면 된다.
 * Channel.CONFLATED: 버퍼 1개, 백그라운드 중 이벤트 유실 없음
 */
@Singleton
class AuthSessionManager @Inject constructor() {
    private val _sessionExpired = Channel<Unit>(Channel.CONFLATED)
    val sessionExpired: Flow<Unit> = _sessionExpired.receiveAsFlow()

    fun notifySessionExpired() {
        _sessionExpired.trySend(Unit)
    }
}
