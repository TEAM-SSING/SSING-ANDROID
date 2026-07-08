package com.ssing.presentation.instructorhome

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
sealed interface Grade {
    val label: String
    val icon: Int

    data class Grade1(
        override val label: String = "Grade1",
        override val icon: Int = R.drawable.img_grade1_badge
    ) : Grade

    data class Grade2(
        override val label: String = "Grade2",
        override val icon: Int = R.drawable.img_grade2_badge
    ) : Grade

    data class Grade3(
        override val label: String = "Grade3",
        override val icon: Int = R.drawable.img_grade3_badge
    ) : Grade

    data class Grade4(
        override val label: String = "Grade4",
        override val icon: Int = R.drawable.img_grade4_badge
    ) : Grade

    data class Grade5(
        override val label: String = "Grade5",
        override val icon: Int = R.drawable.img_grade5_badge
    ) : Grade
}