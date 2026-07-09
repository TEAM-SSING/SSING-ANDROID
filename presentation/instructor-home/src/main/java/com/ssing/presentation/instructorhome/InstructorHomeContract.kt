package com.ssing.presentation.instructorhome

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import com.ssing.core.ui.R

internal interface InstructorHomeContract {

    @Immutable
    data class State(
        val isLoading: Boolean = false,
    )

    sealed interface Effect {
        data class ShowToast(val message: String) : Effect
        data object NavigateToMatching : Effect
    }
}

enum class Grade (
    val label: String,
    @get:DrawableRes val icon: Int
) {
    GRADE1(
        label = "Grade1",
        icon = R.drawable.img_grade1_badge
    ),
    GRADE2(
        label = "Grade2",
        icon = R.drawable.img_grade2_badge
    ),
    GRADE3(
        label = "Grade3",
        icon = R.drawable.img_grade3_badge
    ),
    GRADE4(
        label = "Grade4",
        icon = R.drawable.img_grade4_badge
    ),
    GRADE5(
        label = "Grade5",
        icon = R.drawable.img_grade5_badge
    ),
}