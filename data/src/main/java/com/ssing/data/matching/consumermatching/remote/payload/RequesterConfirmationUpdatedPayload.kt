package com.ssing.data.matching.consumermatching.remote.payload

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class RequesterConfirmationUpdatedPayload(
    @SerialName("progressSummary") val progressSummary: ProgressSummaryPayload,
)
