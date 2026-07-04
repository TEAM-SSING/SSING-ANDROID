package com.ssing.core.notification

import com.google.firebase.messaging.FirebaseMessaging
import com.ssing.data.repository.NotificationRepository
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume

@Singleton
class NotificationTokenProvider @Inject constructor(
    private val notificationRepository: NotificationRepository,
) {

    suspend fun getToken(token: String) {
        notificationRepository.saveNotificationToken(token)
            .onFailure { Timber.e(it, "FCM 토큰 저장 실패") }
    }

    suspend fun getCurrentToken() {
        val token = getToken() ?: return
        getToken(token)
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