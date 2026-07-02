package com.ssing.data.remote.datasource

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.remote.dto.NotificationRequestDto

interface NotificationDataSource {
    suspend fun postNotificationToken(request: NotificationRequestDto): BaseResponse<Unit>

    suspend fun deleteNotificationToken(token: String): BaseResponse<Unit>
}