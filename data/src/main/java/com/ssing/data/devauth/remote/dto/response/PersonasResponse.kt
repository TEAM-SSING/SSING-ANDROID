package com.ssing.data.devauth.remote.dto.response

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class PersonasResponse(
    @SerialName("personas") val personas: List<Personas>,
)

@Serializable
data class Personas(
    @SerialName("personaKey") val personaKey: String,
)
