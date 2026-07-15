package com.ssing.data.consumerlesson.remote.dto.response.cancelinfo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConsumerLessonCancelInfo(
    @SerialName("canceledAt")
    val canceledAt: String,
    @SerialName("canceledBy")
    val canceledBy: CanceledBy,
    @SerialName("cancelReason")
    val cancelReason: String,
)

@Serializable
data class CanceledBy(
    @SerialName("memberId")
    val memberId: Long,
    @SerialName("name")
    val name: String,
)