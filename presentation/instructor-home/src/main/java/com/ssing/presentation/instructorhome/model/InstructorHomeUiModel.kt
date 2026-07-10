package com.ssing.presentation.instructorhome.model

import com.ssing.core.ui.common.component.HomeLessonCardState
import kotlinx.collections.immutable.ImmutableList

data class InstructorHomeUiModel(
    val member: Int,
    val lessonCards: ImmutableList<HomeLessonCardState>,
)