package com.ssing.core.notification

import com.google.firebase.messaging.FirebaseMessaging
import com.ssing.data.repository.NotificationRepository
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume

/**
 * PushNotificationService
 * fcm 푸시 알림 수신 및 기기 토큰 갱신
 *
 * 새로운 FCM 토큰이 발급되었을 때 NotificationTokenProvider를 통해 서버에 동기화 요청
 * 백그라운드 및 포그라운드 상태에서 서버가 보낸 메시지를 수신하여 상단 알림 팝업 노출
 */

@Singleton
class NotificationTokenProvider @Inject constructor(
    private val notificationRepository: NotificationRepository,
) {

    suspend fun getCurrentToken() {
        val token = getToken() ?: return
        notificationRepository.saveNotificationToken(token)
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