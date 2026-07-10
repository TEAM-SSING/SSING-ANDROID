package com.ssing.presentation.instructorlessondetail

import com.ssing.core.ui.base.BaseViewModel
import com.ssing.core.ui.common.component.CancelReason
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class LessonDetailViewModel @Inject constructor() :
    BaseViewModel<LessonDetailContract.State, LessonDetailContract.Effect>(
        LessonDetailContract.State()
    ) {

    fun onBackClick() = sendEffect(LessonDetailContract.Effect.NavigateBack)

    fun onReadyButtonClick() {
        updateState {
            copy(showReadyDialog = true)
        }
    }

    fun onReadyDialogDismiss() {
        updateState {
            copy(showReadyDialog = false)
        }
    }

    fun onEndClick() {
        updateState {
            copy(showLessonEndDialog = true)
        }
    }

    fun onLessonEndDialogDismiss() {
        updateState {
            copy(showLessonEndDialog = false)
        }
    }

    fun onChatRoomClick() {
        updateState {
            copy(showLessonEndDialog = false)
        }
    }

    fun onReadyClick() {
        val before =
            (uiState.value.phase as? LessonDetailContract.LessonDetailPhase.LessonDetailBefore)
                ?.before ?: return
        updateState {
            copy(
                phase = LessonDetailContract.LessonDetailPhase.LessonDetailBefore(
                    before = before.copy(isInstructorReady = true)
                ),
                showReadyDialog = false,
            )
        }
    }

    fun onContinueClick() {
        updateState {
            copy(showLessonEndDialog = false)
        }
    }


    fun onCancelReasonSelect(reason: CancelReason) {
        updateState {
            copy(cancelReasonState = cancelReasonState.copy(selectedReason = reason))
        }
    }
}
