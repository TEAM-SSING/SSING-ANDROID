package com.ssing.core.notification.data.repository.impl

import com.ssing.core.network.token.TokenAccessManager
import com.ssing.core.notification.ClientApp
import com.ssing.core.notification.data.remote.datasource.NotificationDataSource
import com.ssing.core.notification.data.remote.dto.FcmTokenRegisterRequest
import com.ssing.core.notification.data.remote.dto.FcmTokenUnregisterRequest
import com.ssing.core.notification.data.repository.NotificationRepository
import timber.log.Timber
import javax.inject.Inject


class NotificationRepositoryImpl @Inject constructor(
    private val dataSource: NotificationDataSource,
    private val tokenAccessManager: TokenAccessManager,
    private val clientApp: ClientApp,
) : NotificationRepository {

    override suspend fun registerFcmToken(token: String): Result<Unit> {
        val accessToken = tokenAccessManager.getAccessToken()
        if (accessToken == null) {
            Timber.d("로그인 상태가 아니라 FCM 토큰을 서버에 전송하지 않음")
            return Result.success(Unit)
        }

        return dataSource.registerFcmToken(
            FcmTokenRegisterRequest(
                clientApp = clientApp.name,
                platform = PLATFORM_ANDROID,
                fcmToken = token,
            ),
        )
    }

    override suspend fun unregisterFcmToken(token: String): Result<Unit> =
        dataSource.unregisterFcmToken(FcmTokenUnregisterRequest(fcmToken = token))

    companion object {
        private const val PLATFORM_ANDROID = "ANDROID"
    }
}
