package com.ssing.data.consumerlesson.remote.dto.response.instructorprofile

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ConsumerLessonInstructorProfile(
    @SerialName("instructorId")
    val instructorId: Long,
    @SerialName("name")
    val name: String,
    @SerialName("gender")
    val gender: String,
    @SerialName("birthYear")
    val birthYear: Int,
    @SerialName("level")
    val level: Int,
    @SerialName("profileImageUrl")
    val profileImageUrl: String,
)