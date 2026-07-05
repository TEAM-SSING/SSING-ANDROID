package com.ssing.data.instructorlogin.remote.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KakaoLoginRequest(
    @SerialName("kakaoAccessToken") val kakaoAccessToken: String,
)
