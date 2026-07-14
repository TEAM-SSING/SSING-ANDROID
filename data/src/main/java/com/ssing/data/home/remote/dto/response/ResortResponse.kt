package com.ssing.data.home.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ResortResponse(
    @SerialName("code") val code: String,
    @SerialName("displayName") val displayName: String,
)
