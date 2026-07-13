package com.ssing.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseEnvelope<T>(
    @SerialName("eventId")
    val eventId: String,
    @SerialName("eventType")
    val eventType: String,
    @SerialName("occurredAt")
    val occurredAt: String,
    @SerialName("recipientRole")
    val recipientRole: String,
    @SerialName("payload")
    val payload: T,
    @SerialName("matchingRequestId")
    val matchingRequestId: Long? = null,
    @SerialName("groupId")
    val groupId: Long? = null,
    @SerialName("offerId")
    val offerId: Long? = null,
    @SerialName("matchingStatus")
    val matchingStatus: String? = null,
)
