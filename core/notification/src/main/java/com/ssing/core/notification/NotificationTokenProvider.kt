package com.ssing.core.notification

import com.google.firebase.messaging.FirebaseMessaging
import com.ssing.core.notification.data.repository.NotificationRepository
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume

/**
 * Firebase 라이브러리 의존성을 캡슐화하고, 현재 기기의 FCM 토큰을 조회해
 * 서버에 등록/해제하는 컴포넌트.
 *
 * - 로그인 성공 시 [registerCurrentToken] 로 토큰 등록
 * - 로그아웃 시 [unregisterCurrentToken] 로 토큰 해제(Access Token 제거 전에 호출)
 */
@Singleton
class NotificationTokenProvider @Inject constructor(
    private val notificationRepository: NotificationRepository,
) {

    suspend fun registerCurrentToken() {
        val token = getToken() ?: return
        notificationRepository.registerFcmToken(token)
            .onFailure { Timber.e(it, "FCM 토큰 등록 실패") }
    }

    suspend fun unregisterCurrentToken() {
        val token = getToken() ?: return
        notificationRepository.unregisterFcmToken(token)
            .onFailure { Timber.e(it, "FCM 토큰 해제 실패") }
    }

    private suspend fun getToken(): String? =
        suspendCancellableCoroutine { continuation ->
            FirebaseMessaging.getInstance().token
                .addOnSuccessListener { continuation.resume(it) }
                .addOnFailureListener {
                    Timber.e(it, "FCM 토큰 발급 실패")
                    continuation.resume(null)
                }
        }
}
