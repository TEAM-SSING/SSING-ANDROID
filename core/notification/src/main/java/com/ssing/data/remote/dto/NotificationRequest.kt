package com.ssing.data.remote.dto

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
@OptIn(InternalSerializationApi::class)
data class NotificationRequest(
    @SerialName("token")
    val token: String,
    )