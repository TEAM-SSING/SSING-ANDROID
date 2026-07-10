package com.ssing.presentation.consumerhome

import androidx.lifecycle.viewModelScope
import com.ssing.core.ui.base.BaseViewModel
import com.ssing.core.ui.common.component.HomeLessonCardState
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
            ConsumerHomeContract.Effect.NavigateToLessonDetail(lessonId = lesson.lessonId)
        }
    }

    fun onReservationClick() {
        viewModelScope.launch {
            sendEffect(ConsumerHomeContract.Effect.ShowToast("준비 중인 기능이에요."))
        }
    }
}
