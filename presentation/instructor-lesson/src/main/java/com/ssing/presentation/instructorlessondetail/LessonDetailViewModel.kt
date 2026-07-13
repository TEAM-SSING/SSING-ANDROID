package com.ssing.presentation.instructorlessondetail

import androidx.lifecycle.viewModelScope
import com.ssing.core.network.exception.ApiException
import com.ssing.core.ui.base.BaseViewModel
import com.ssing.core.ui.common.component.CancelReason
import com.ssing.core.ui.extension.uiMessage
import com.ssing.data.lesson.repository.api.LessonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class LessonDetailViewModel @Inject constructor(
    private val lessonRepository: LessonRepository,
) :
    BaseViewModel<LessonDetailContract.State, LessonDetailContract.Effect>(
        LessonDetailContract.State()
    ) {

    fun onBackClick() = sendEffect(LessonDetailContract.Effect.NavigateBack)

    fun onCancelClassClick() {
        updateState {
            copy(showReadyDialog = false, showLessonEndDialog = false)
        }
    }

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

        viewModelScope.launch {
            lessonRepository.lessonStart(before.lessonId)
                .onSuccess {
                    updateState {
                        copy(
                            phase = LessonDetailContract.LessonDetailPhase.LessonDetailBefore(
                                before = before.copy(isInstructorReady = true)
                            ),
                            showReadyDialog = false,
                        )
                    }
                }
                .onFailure {
                    if (it is ApiException) {
                        sendEffect(LessonDetailContract.Effect.ShowToast(it.uiMessage))
                    }
                    updateState { copy(showReadyDialog = false) }
                }
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

    fun onCancelSheetOpen() {
        updateState {
            copy(cancelReasonState = cancelReasonState.copy(visible = true))
        }
    }

    fun onCancelSheetDismiss() {
        updateState {
            copy(
                cancelReasonState = cancelReasonState.copy(
                    visible = false,
                    selectedReason = null
                )
            )
        }
    }

    fun onCancelConfirmClick(customReason: String?) {
        val reason = uiState.value.cancelReasonState.selectedReason ?: return
        updateState {
            copy(
                cancelReasonState = cancelReasonState.copy(
                    visible = false,
                    selectedReason = null
                )
            )
        }
    }
}