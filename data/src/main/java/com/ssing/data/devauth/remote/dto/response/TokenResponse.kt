package com.ssing.data.devauth.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class TokenResponse(
    @SerialName("accessToken") val accessToken: String,
    @SerialName("refreshToken") val refreshToken: String,
    @SerialName("tokenType") val tokenType: String,
    @SerialName("expiresIn") val expiresIn: Int,
    @SerialName("member") val member: JsonElement,
    @SerialName("devMeta") val devMeta: DevMeta,
)

@Serializable
data class DevMeta(
    @SerialName("personaOrigin") val personaOrigin: String,
    @SerialName("accountState") val accountState: String,
)
