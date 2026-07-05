package com.ssing.data.instructorlogin.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MemberResponse(
    @SerialName("id") val id: Long,
    @SerialName("nickname") val nickname: String,
    @SerialName("role") val role: String,
    @SerialName("memberStatus") val memberStatus: String,
    @SerialName("instructorStatus") val instructorStatus: String,
)
