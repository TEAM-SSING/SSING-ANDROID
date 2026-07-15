package com.ssing.data.lesson.common.remote.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class LessonCancelRequest(
    @SerialName("cancelReason") val cancelReason: String,
    @SerialName("cancelReasonDetail") val cancelReasonDetail: String? = null,
)