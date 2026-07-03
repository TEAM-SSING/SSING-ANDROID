package com.ssing.data.repository.impl

import com.ssing.data.remote.datasource.NotificationDataSource
import com.ssing.data.remote.dto.NotificationRequestDto
import com.ssing.data.repository.NotificationRepository
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val dataSource: NotificationDataSource
) : NotificationRepository {

    override suspend fun saveNotificationToken(token: String): Result<Unit> = runCatching {
        dataSource.postNotificationToken(NotificationRequestDto(token = token))
    }
}
