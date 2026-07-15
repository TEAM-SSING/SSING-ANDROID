package com.ssing.data.matching.consumermatching.remote.payload

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class InstructorPayload(
    @SerialName("instructorProfileId") val instructorProfileId: Long,
    @SerialName("name") val name: String,
    @SerialName("profileImageUrl") val profileImageUrl: String? = null,
)
