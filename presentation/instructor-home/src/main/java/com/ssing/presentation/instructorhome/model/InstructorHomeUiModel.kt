package com.ssing.presentation.instructorhome.model

import androidx.annotation.DrawableRes
import com.ssing.core.ui.R
import com.ssing.core.ui.common.component.HomeLessonCardState
import kotlinx.collections.immutable.ImmutableList

data class InstructorHomeUiModel(
    val member: Int,
    val lessonCards: ImmutableList<HomeLessonCardState>,
)

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

data class ReviewModel(
    val averageRating: Float,
    val grade: Grade,
    val achievementRate: Int,
)