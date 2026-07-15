package com.ssing.data.matching.consumermatching.remote.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ConsumerMatchingConfirmationRequest(
    @SerialName("decision") val decision: String,
)
