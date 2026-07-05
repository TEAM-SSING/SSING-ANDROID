package com.ssing.data.instructorlogin.model

data class InstructorLoginResult(
    val id: Long,
    val nickname: String,
    val role: String,
    val memberStatus: String,
    val instructorStatus: String,
)
