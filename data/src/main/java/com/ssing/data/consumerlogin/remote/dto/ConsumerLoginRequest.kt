package com.ssing.data.consumerlogin.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConsumerLoginRequest(
    @SerialName("kakaoAccessToken")
    val kakaoAccessToken: String,
)