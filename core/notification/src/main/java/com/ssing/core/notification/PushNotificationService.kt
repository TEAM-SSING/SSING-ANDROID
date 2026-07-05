package com.ssing.core.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

/**
 * 디바이스 fcm 토큰 조회 및 서버 동기화 관리 컴포넌트
 *
 * firebase 라이브러리와 의존성을 캡슐화, 최신 토큰을 제공하는 역할
 *
 * 유저 회원가입/로그인 성공 시 서버에 디바이스 토큰 등록
 * PushNotificationService.onNewToken에서 새 토큰 갱신 시 사용
 *
 * @param notificationRepository fcm 토큰 저장 및 서버 전송을 위한 레포지토리
 * @param token 백엔드 서버에 저장하거나 firebase로부터 발급받은 fcm 토큰 문자열
 * @param continuation 비동기 Task 기반의 토큰 발급 작업을 코루틴 흐름으로 변환하기 위한 중단 상태 제어 객체
 */

@AndroidEntryPoint
class PushNotificationService : FirebaseMessagingService() {

    @Inject
    lateinit var notificationTokenProvider: NotificationTokenProvider

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        serviceScope.launch {
            notificationTokenProvider.getToken(token)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        createChannels(this)
        val title = message.data["title"] ?: return
        val body = message.data["body"] ?: return
        showNotification(title, body)
    }

    private fun showNotification(title: String, body: String) {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val launchIntent = packageManager.getLaunchIntentForPackage(packageName)?.apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        val id = System.currentTimeMillis().toInt()
        val pendingIntent = launchIntent?.let {
            PendingIntent.getActivity(
                this,
                id,
                it,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )
        }
        val notification = NotificationCompat.Builder(this, DEFAULT_CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(body)
            .apply { pendingIntent?.let { setContentIntent(it) } }
            .setAutoCancel(true)
            .build()

        notificationManager.notify(id, notification)
    }

    companion object {
        private const val DEFAULT_CHANNEL_ID = "default_channel"
        private const val DEFAULT_CHANNEL_NAME = "기본 알림"
        private val channelsCreated = AtomicBoolean(false)

        fun createChannels(context: Context) {
            if (!channelsCreated.compareAndSet(false, true)) return

            val notificationManager =
                context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    DEFAULT_CHANNEL_ID,
                    DEFAULT_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
                ),
            )
        }
    }
}
