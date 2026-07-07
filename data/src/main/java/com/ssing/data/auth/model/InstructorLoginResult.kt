package com.ssing.data.auth.model

data class InstructorLoginResult(
    val id: Long,
    val nickname: String,
    val role: String,
    val memberStatus: String,
    val instructorStatus: String,
)
