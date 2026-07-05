package com.ssing.data.devauth.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenRequest (
    @SerialName("personaKey") val personaKey: String,
    @SerialName("autoCreate") val autoCreate: Boolean,
)
