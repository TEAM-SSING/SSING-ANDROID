package com.ssing.presentation.instructorhome

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import com.ssing.core.ui.R
import com.ssing.core.ui.common.component.HomeLessonCardState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal interface InstructorHomeContract {

    @Immutable
    data class State(
        val lessonId: Long? = 0,
        val offerId: Long? = 0,
        val isLoading: Boolean = false,
        val lessonCards: ImmutableList<HomeLessonCardState> = persistentListOf(),
        val hasUnreadNotification: Boolean = false,
        val instructorName: String = "",
        val matchingPeopleCount: Long = 0,
        val averageRating: Float = 0f,
        val grade: Grade = Grade.GRADE1,
        val achievementRate: Int = 0,
    )

    sealed interface Effect {
        data class ShowToast(val message: String) : Effect
        data object NavigateToMatching : Effect
        data class NavigateToLessonDetail(
            val lessonId: Long? = null,
            val offerId: Long? = null,
        ) : Effect
        data object NavigateToMatchingWaiting : Effect
    }
}

enum class Grade (
    val label: String,
    @get:DrawableRes val icon: Int
) {
    GRADE1(
        label = "Grade1",
        icon =  R.drawable.img_grade1_badge
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
