package com.ssing.presentation.consumerlesson.model

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class LessonInfoUiModel(
    val tags: ImmutableList<String>,
    val teamNicknames: ImmutableList<String>,
    val totalCount: Int,
    val place: String,
    val duration: String,
    val price: Int,
)

@Immutable
data class CompletedLessonInfoUiModel(
    val lessonInfo: LessonInfoUiModel,
    val actualTimeRange: String,
)

@Immutable
data class CanceledLessonInfoUiModel(
    val lessonInfo: LessonInfoUiModel,
    val cancelDateTime: String,
    val cancelSubject: String,
    val cancelReason: String,
)