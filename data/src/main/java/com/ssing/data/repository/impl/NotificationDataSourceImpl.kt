package com.ssing.data.repository.impl

import com.ssing.core.network.model.BaseResponse
import com.ssing.core.network.util.ApiResponseHandler
import com.ssing.data.remote.datasource.NotificationDataSource
import com.ssing.data.remote.dto.NotificationRequestDto
import com.ssing.data.repository.api.NotificationApi
import javax.inject.Inject

class NotificationDataSourceImpl @Inject constructor(
    private val apiResponseHandler: ApiResponseHandler,
    private val api: NotificationApi,
) : NotificationDataSource {

    override suspend fun postNotificationToken(request: NotificationRequestDto): BaseResponse<Unit> =
        api.postNotificationToken(request)

    override suspend fun deleteNotificationToken(token: String): BaseResponse<Unit> =
        api.deleteNotificationToken(token)
}
