package com.ssing.data.consumerlesson.remote.dto.response

import com.ssing.data.consumerlesson.remote.dto.response.cancelinfo.ConsumerLessonCancelInfo
import com.ssing.data.consumerlesson.remote.dto.response.instructorprofile.ConsumerLessonInstructorProfile
import com.ssing.data.consumerlesson.remote.dto.response.lessoninfo.Resort
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConsumerLessonDetailCanceledResponse(
    @SerialName("lessonId") val lessonId: Long,
    @SerialName("lessonInfo") val lessonInfo: LessonInfo,
    @SerialName("instructorProfile") val instructorProfile: ConsumerLessonInstructorProfile,
    @SerialName("cancelInfo") val cancelInfo: ConsumerLessonCancelInfo,
) : ConsumerLessonDetailResponse {

    @Serializable
    data class LessonInfo(
        @SerialName("representativeConsumerNames") val representativeConsumerNames: List<String>,
        @SerialName("totalHeadcount") val totalHeadcount: Int,
        @SerialName("resort") val resort: Resort,
        @SerialName("sport") val sport: String,
        @SerialName("lessonLevel") val lessonLevel: String,
        @SerialName("lessonDurationMinutes") val lessonDurationMinutes: Int,
        @SerialName("myTeamLessonPrice") val myTeamLessonPrice: Int,
    )
}