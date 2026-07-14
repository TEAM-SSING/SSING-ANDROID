package com.ssing.data.matching.instructormatching.model
data class InstructorMatchingSocketEvent(
    val eventType: String,
    val offerId: Long?,
    val groupId: Long?,
)
