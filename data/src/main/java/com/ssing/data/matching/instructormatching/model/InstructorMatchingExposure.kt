package com.ssing.data.matching.instructormatching.model

data class InstructorMatchingExposure(
    val resort: InstructorMatchingResort,
    val availableSports: List<String>,
)

data class InstructorMatchingResort(
    val code: String,
    val displayName: String,
)
