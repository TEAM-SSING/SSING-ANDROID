package com.ssing.data.matching.consumermatching.remote.payload

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class InstructorAcceptedPayload(
    @SerialName("instructor") val instructor: InstructorPayload,
    @SerialName("lessonSummary") val lessonSummary: LessonSummaryPayload,
)
