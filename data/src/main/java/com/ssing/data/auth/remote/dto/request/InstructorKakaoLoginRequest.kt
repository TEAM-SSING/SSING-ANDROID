package com.ssing.data.auth.remote.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InstructorKakaoLoginRequest(
    @SerialName("kakaoAccessToken") val kakaoAccessToken: String,
)
