package com.ssing.presentation.consumerhome

import androidx.lifecycle.viewModelScope
import com.ssing.core.ui.base.BaseViewModel
import com.ssing.core.ui.common.component.HomeLessonCardState
import com.ssing.core.ui.common.component.HomeLessonCardState.Reservation.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ConsumerHomeViewModel @Inject constructor() :
    BaseViewModel<ConsumerHomeContract.State, ConsumerHomeContract.Effect>(
        ConsumerHomeContract.State()
    ) {

    fun onMatchingClick() {
        viewModelScope.launch {
            sendEffect(ConsumerHomeContract.Effect.NavigateToMatching)
        }
    }

    fun onLessonClick(
        lesson: HomeLessonCardState.Reservation,
    ) {
        viewModelScope.launch {
            when (lesson.status) {
                Status.Default -> {
                    sendEffect(
                        ConsumerHomeContract.Effect.NavigateToLessonDetail(
                            lessonStatus = lesson.status,
                        )
                    )
                }

                Status.Matching,
                Status.Matched,
                    -> {
                    sendEffect(
                        ConsumerHomeContract.Effect.NavigateToLessonDetail(
                            lessonStatus = lesson.status,
                        )
                    )
                }
            }

//            sendEffect(ConsumerHomeContract.Effect.NavigateToLessonDetail(lessonStatus = lesson.status))
        }
    }

    fun onReservationClick() {
        viewModelScope.launch {
            sendEffect(ConsumerHomeContract.Effect.ShowToast("준비 중인 기능이에요."))
        }
    }
}
