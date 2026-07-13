package com.ssing.data.devauth.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class TokenResponse(
    @SerialName("accessToken") val accessToken: String,
    @SerialName("refreshToken") val refreshToken: String,
    @SerialName("tokenType") val tokenType: String,
    @SerialName("expiresIn") val expiresIn: Int,
    @SerialName("persona") val persona: Persona,
)

@Serializable
internal data class Persona(
    @SerialName("personaKey") val personaKey: String,
    @SerialName("nickname") val nickname: String,
    @SerialName("template") val template: String,
    @SerialName("role") val role: String,
    @SerialName("memberStatus") val memberStatus: String,
    @SerialName("instructorStatus") val instructorStatus: String,
)
