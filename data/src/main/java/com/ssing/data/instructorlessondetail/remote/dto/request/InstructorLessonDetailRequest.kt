package com.ssing.data.instructorlessondetail.remote.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class InstructorLessonDetailRequest(
    @SerialName("lessonId")
    val lessonId: Long,
)