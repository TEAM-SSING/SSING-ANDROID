package com.ssing.presentation.consumerhome

import com.ssing.core.ui.base.BaseViewModel
import com.ssing.core.ui.common.component.HomeLessonCardState
import com.ssing.data.consumerhome.repository.api.ConsumerHomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class ConsumerHomeViewModel @Inject constructor(
    private val consumerHomeRepository: ConsumerHomeRepository,
) :
    BaseViewModel<ConsumerHomeContract.State, ConsumerHomeContract.Effect>(
        ConsumerHomeContract.State()
    ) {

    fun onMatchingClick() {
        sendEffect(ConsumerHomeContract.Effect.NavigateToMatching)

    }

    fun onLessonClick(
        lesson: HomeLessonCardState.Reservation,
    ) {
        sendEffect(ConsumerHomeContract.Effect.NavigateToLessonDetail(lessonId = lesson.lessonId))
    }

    fun onReservationClick() {
        sendEffect(
            ConsumerHomeContract.Effect.ShowToast("준비 중인 기능이에요.")
        )
    }
}
