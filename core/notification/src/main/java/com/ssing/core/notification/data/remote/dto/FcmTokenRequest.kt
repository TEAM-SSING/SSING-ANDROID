package com.ssing.core.notification.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * [PUT] /api/v1/fcm-tokens 등록/갱신 요청 body.
 */
@Serializable
data class FcmTokenRegisterRequest(
    @SerialName("clientApp")
    val clientApp: String,
    @SerialName("platform")
    val platform: String,
    @SerialName("fcmToken")
    val fcmToken: String,
)

/**
 * [POST] /api/v1/fcm-tokens/unregister 해제 요청 body.
 */
@Serializable
data class FcmTokenUnregisterRequest(
    @SerialName("fcmToken")
    val fcmToken: String,
)
