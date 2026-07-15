package com.ssing.presentation.consumerlesson.mapper

import com.ssing.core.ui.type.displayGender
import com.ssing.data.consumerlesson.model.InstructorProfile
import com.ssing.presentation.consumerlesson.model.InstructorProfileUiModel
import java.time.Year

internal fun InstructorProfile.toUiModel(): InstructorProfileUiModel = InstructorProfileUiModel(
    name = name,
    age = Year.now().value - birthYear,
    gender = displayGender(gender),
    level = "grade$level",
    imageUrl = profileImageUrl,
)