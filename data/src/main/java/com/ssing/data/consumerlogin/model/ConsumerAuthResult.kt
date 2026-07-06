package com.ssing.data.consumerlogin.model

data class ConsumerAuthResult(
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String,
    val expiresIn: Long,
    val memberId: Long,
    val nickname: String,
    val role: String,
    val memberStatus: String,
)