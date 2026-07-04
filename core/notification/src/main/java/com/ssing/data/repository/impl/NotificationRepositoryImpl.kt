package com.ssing.data.repository.impl

import com.ssing.core.network.token.TokenAccessManager
import com.ssing.data.remote.datasource.NotificationDataSource
import com.ssing.data.remote.dto.NotificationRequestDto
import com.ssing.data.repository.NotificationRepository
import timber.log.Timber
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val dataSource: NotificationDataSource,
    private val tokenAccessManager: TokenAccessManager,
) : NotificationRepository {

    override suspend fun saveNotificationToken(token: String): Result<Unit> {
        val accessToken = tokenAccessManager.getAccessToken()
        if (accessToken == null) {
            Timber.d("로그인 상태가 아니라 FCM 토큰을 서버에 전송하지 않음")
            return Result.success(Unit)
        }

        return dataSource.postNotificationToken(NotificationRequestDto(token = token))
    }
}