package com.ssing.data.consumerlesson.remote.dto.response

import com.ssing.data.consumerlesson.remote.dto.response.instructorprofile.ConsumerLessonInstructorProfile
import com.ssing.data.consumerlesson.remote.dto.response.lessoninfo.ConsumerLessonInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConsumerLessonDetailCompletedResponse(
    @SerialName("lessonId") val lessonId: Long,
    @SerialName("lessonInfo") val lessonInfo: ConsumerLessonInfo,
    @SerialName("instructorProfile") val instructorProfile: ConsumerLessonInstructorProfile,
) : ConsumerLessonDetailResponse