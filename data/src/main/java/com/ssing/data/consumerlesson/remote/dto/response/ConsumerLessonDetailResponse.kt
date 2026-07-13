package com.ssing.data.consumerlesson.remote.dto.response

import com.ssing.data.consumerlesson.remote.dto.response.cancelinfo.ConsumerLessonCancelInfo
import com.ssing.data.consumerlesson.remote.dto.response.instructorprofile.ConsumerLessonInstructorProfile
import com.ssing.data.consumerlesson.remote.dto.response.lessoninfo.ConsumerLessonInfo
import com.ssing.data.consumerlesson.remote.dto.response.matchingrequest.ConsumerLessonMatchingRequest
import com.ssing.data.consumerlesson.remote.dto.response.statusinfo.ConsumerLessonStatusInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConsumerLessonDetailResponse(
    @SerialName("lessonId")
    val lessonId: Long,
    @SerialName("lessonStatus")
    val lessonStatus: String,
    @SerialName("statusInfo")
    val statusInfo: ConsumerLessonStatusInfo? = null,
    @SerialName("cancelInfo")
    val cancelInfo: ConsumerLessonCancelInfo? = null,
    @SerialName("lessonInfo")
    val lessonInfo: ConsumerLessonInfo? = null,
    @SerialName("instructorProfile")
    val instructorProfile: ConsumerLessonInstructorProfile? = null,
    @SerialName("matchingRequests")
    val matchingRequests: List<ConsumerLessonMatchingRequest>? = null,
    )