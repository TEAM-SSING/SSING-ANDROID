package com.ssing.presentation.consumerlesson

import androidx.lifecycle.viewModelScope
import com.ssing.core.ui.base.BaseViewModel
import com.ssing.core.ui.common.component.CancelReason
import com.ssing.core.ui.common.component.LessonBannerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ConsumerLessonViewModel @Inject constructor() :
    BaseViewModel<ConsumerLessonContract.State, ConsumerLessonContract.Effect>(
        ConsumerLessonContract.State()
    ) {
    fun onReadyClick() {
        updateState { copy(isReady = true) }
    }

    fun onCancelClick() {
        updateState { copy(showCancelConfirmSheet = true) }
    }

    fun onReasonSelected(reason: CancelReason) {
        updateState { copy(selectedReason = reason) }
    }

    fun onCancelConfirmed(
        etcReason: String? = null,
    ) {
        viewModelScope.launch {
            updateState {
                copy(
                    lessonBannerState = LessonBannerState.Canceled,
                    showCancelConfirmSheet = false,
                    selectedReason = null,
                    etcReason = etcReason,
                    // TODO: LessonBannerState를 canceled로 변경
                )
            }
        }
    }

    fun onCancelDismiss() {
        updateState { copy(showCancelConfirmSheet = false) }
    }

    fun onLessonStarted(remainingTime: String, elapsedTime: String) {
        updateState {
            copy(
                lessonBannerState = LessonBannerState.Ongoing(
                    remainingTime = remainingTime,
                    elapsedTime = elapsedTime,
                ),
                participantTeams = participantTeams
                    .map { it.copy(isReady = false) }
                    .toPersistentList(),
            )
        }
    }
}