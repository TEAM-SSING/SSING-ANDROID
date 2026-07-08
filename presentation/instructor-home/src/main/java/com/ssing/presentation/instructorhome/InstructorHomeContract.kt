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

sealed interface Grade {
    val label: String
    @get:DrawableRes val icon: Int

    data object Grade1 : Grade {
        override val label = "Grade1"
        override val icon = R.drawable.img_grade1_badge
    }

    data object Grade2 : Grade {
        override val label = "Grade2"
        override val icon = R.drawable.img_grade2_badge
    }

    data object Grade3 : Grade {
        override val label = "Grade3"
        override val icon = R.drawable.img_grade3_badge
    }

    data object Grade4 : Grade {
        override val label = "Grade4"
        override val icon = R.drawable.img_grade4_badge
    }

    data object Grade5 : Grade {
        override val label = "Grade5"
        override val icon = R.drawable.img_grade5_badge
    }
}