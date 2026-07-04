package com.ssing.data.remote.dto

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@OptIn(InternalSerializationApi::class)
data class NotificationRequestDto(
    @SerialName("token")
    val token: String,
    @SerialName("platform")
    val platform: String = ANDROID,
) {
    companion object {
        private const val ANDROID = "ANDROID"
    }
}