package com.ssing.presentation.instructorlessondetail

import com.ssing.core.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class LessonDetailViewModel @Inject constructor() :
    BaseViewModel<LessonDetailContract.State, LessonDetailContract.Effect>(
        LessonDetailContract.State()
    ) {

    fun onBackClick() = sendEffect(LessonDetailContract.Effect.NavigateBack)

    fun onCancelClassClick() {
        updateState {
            copy(dialog = null)
        }
    }

    fun onReadyButtonClick() {
        updateState {
            copy(dialog = LessonDetailContract.LessonDetailDialog.InstructorReady)
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
                dialog = null,
            )
        }
    }

    fun onDialogDismiss() {
        updateState {
            copy(dialog = null)
        }
    }

    fun onChatRoomClick() {
        updateState {
            copy(dialog = null)
        }
    }

    fun onEndClick() {
        updateState {
            copy(dialog = LessonDetailContract.LessonDetailDialog.LessonEnd)
        }
    }

    fun onContinueClick() {
        updateState {
            copy(dialog = null)
        }
    }
}
