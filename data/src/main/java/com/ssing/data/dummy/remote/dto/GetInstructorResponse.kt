package com.ssing.data.dummy.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetInstructorResponse(
    @SerialName("id") val id: Long?,
    @SerialName("name") val name: String?,
    @SerialName("rating") val rating: Float?,
    @SerialName("career") val career: String?,
    @SerialName("sport") val sport: String?,
    @SerialName("level") val level: String?,
    @SerialName("resort") val resort: String?,
)
