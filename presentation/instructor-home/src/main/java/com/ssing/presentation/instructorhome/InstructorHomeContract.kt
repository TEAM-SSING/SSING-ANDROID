package com.ssing.presentation.instructorhome

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import com.ssing.core.ui.R
import com.ssing.core.ui.common.component.HomeLessonCardState
import com.ssing.presentation.instructorhome.model.InstructorHomeUiModel
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
        val isLoading: Boolean = false,
    )

    sealed interface Effect {
        data class ShowToast(val message: String) : Effect
        data object NavigateToMatching : Effect
        data class NavigateToLessonDetail(val lessonId: Long) : Effect
    }
}