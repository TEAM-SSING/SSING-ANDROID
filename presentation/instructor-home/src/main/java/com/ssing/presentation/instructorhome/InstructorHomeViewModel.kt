package com.ssing.presentation.instructorhome

import com.ssing.core.ui.base.BaseViewModel
import com.ssing.core.ui.common.component.Reservation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class InstructorHomeViewModel @Inject constructor() :
    BaseViewModel<InstructorHomeContract.State, InstructorHomeContract.Effect>(
        InstructorHomeContract.State()
    ) {
    fun onMatchingClick() {
        sendEffect(InstructorHomeContract.Effect.NavigateToMatching)

    }

    fun onLessonClick(
        lesson: Reservation,
    ) {
        sendEffect(InstructorHomeContract.Effect.NavigateToLessonDetail(lessonId = lesson.lessonId))
    }

    fun onReviewClick() {
        sendEffect(
            InstructorHomeContract.Effect.ShowToast("준비 중인 기능이에요.")
        )
    }

    fun onReservationClick() {
        sendEffect(
            InstructorHomeContract.Effect.ShowToast("준비 중인 기능이에요.")
        )
    }
}
