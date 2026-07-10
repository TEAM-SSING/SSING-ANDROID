package com.ssing.presentation.consumerhome.model

import com.ssing.core.ui.common.component.HomeLessonCardState
import kotlinx.collections.immutable.ImmutableList

data class ConsumerHomeUiModel(
    val member: Int,
    val lessonCards: ImmutableList<HomeLessonCardState>,
)