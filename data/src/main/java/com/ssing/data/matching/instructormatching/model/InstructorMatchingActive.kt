package com.ssing.data.matching.instructormatching.model

data class InstructorMatchingActive(
    val offerId: Long?,
    val setting: InstructorMatchingSetting,
)

data class InstructorMatchingSetting(
    val isExposed: Boolean,
    val resort: InstructorMatchingResort,
    val sport: String,
    val lessonLevels: List<String>,
    val availableDurationMinutes: List<Int>,
    val maxHeadcount: Int,
    val equipmentReady: Boolean,
)
