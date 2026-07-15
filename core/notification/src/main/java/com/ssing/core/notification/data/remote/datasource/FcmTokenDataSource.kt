package com.ssing.core.notification.data.remote.datasource

import com.ssing.core.notification.data.remote.dto.FcmTokenRegisterRequest
import com.ssing.core.notification.data.remote.dto.FcmTokenUnregisterRequest


interface FcmTokenDataSource {
    suspend fun registerFcmToken(request: FcmTokenRegisterRequest): Result<Unit>

    suspend fun unregisterFcmToken(request: FcmTokenUnregisterRequest): Result<Unit>
}
