package com.ssing.core.network.session

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 세션 만료(Refresh Token 만료 등) 이벤트를 앱 전역에 전파한다.
 *
 * [sessionExpired]를 MainActivity(또는 앱 레벨 ViewModel)에서 collect해서
 * 로그인 화면으로 이동시키면 된다.
 */
@Singleton
class SessionManager @Inject constructor() {
    private val _sessionExpired = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val sessionExpired: SharedFlow<Unit> = _sessionExpired.asSharedFlow()

    suspend fun notifySessionExpired() {
        _sessionExpired.emit(Unit)
    }
}
