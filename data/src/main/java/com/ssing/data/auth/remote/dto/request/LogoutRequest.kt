package com.ssing.data.auth.remote.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LogoutRequest(
    @SerialName("refreshToken") val refreshToken: String,
)
