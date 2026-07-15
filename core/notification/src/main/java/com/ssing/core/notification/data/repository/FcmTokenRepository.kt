package com.ssing.core.notification.data.repository


interface FcmTokenRepository {
    /** 현재 기기의 FCM 토큰을 서버에 등록/갱신한다. 로그인 상태가 아니면 전송하지 않는다. */
    suspend fun registerFcmToken(token: String): Result<Unit>

    /** 현재 기기의 FCM 토큰을 서버에서 해제한다(로그아웃). */
    suspend fun unregisterFcmToken(token: String): Result<Unit>
}
