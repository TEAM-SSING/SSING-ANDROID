package com.ssing.presentation.instructorhome

import androidx.compose.runtime.Immutable
import com.ssing.core.ui.common.component.HomeLessonCardState
import com.ssing.presentation.instructorhome.model.Grade
import com.ssing.presentation.instructorhome.model.InstructorHomeUiModel
import com.ssing.presentation.instructorhome.model.ReviewModel
import kotlinx.collections.immutable.persistentListOf

internal interface InstructorHomeContract {

    @Immutable
    data class State(
        val home: InstructorHomeUiModel = InstructorHomeUiModel(
            member = 0,
            lessonCards = persistentListOf(
                HomeLessonCardState.Empty
            ),
        ),
        val reviewModel: ReviewModel = ReviewModel(
            averageRating = 0f,
            grade = Grade.GRADE1,
            achievementRate = 0,
        ),
        val isLoading: Boolean = false,
    )

    sealed interface Effect {
        data class ShowToast(val message: String) : Effect
        data object NavigateToMatching : Effect
        data class NavigateToLessonDetail(val lessonId: Long) : Effect
    }
}