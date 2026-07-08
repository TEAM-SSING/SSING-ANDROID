package com.ssing.data.consumerlogin.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConsumerAuthResponse(
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("refreshToken")
    val refreshToken: String,
    @SerialName("tokenType")
    val tokenType: String,
    @SerialName("expiresIn")
    val expiresIn: Long,
    @SerialName("member")
    val member: ConsumerMemberResponse,
)

@Serializable
data class ConsumerMemberResponse(
    @SerialName("id")
    val id: Long,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("role")
    val role: String,
    @SerialName("memberStatus")
    val memberStatus: String,
)