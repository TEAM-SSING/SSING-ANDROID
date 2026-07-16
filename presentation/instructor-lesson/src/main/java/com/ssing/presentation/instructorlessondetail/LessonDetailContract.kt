package com.ssing.presentation.instructorlessondetail

import androidx.compose.runtime.Immutable
import com.ssing.presentation.instructorlessondetail.model.LessonDetailBeforeUiModel
import com.ssing.presentation.instructorlessondetail.model.LessonDetailCanceledUiModel
import com.ssing.presentation.instructorlessondetail.model.LessonDetailCompletedUiModel
import com.ssing.presentation.instructorlessondetail.model.LessonDetailOngoingUiModel
import com.ssing.presentation.instructorlessondetail.screen.CancelReasonState

internal interface LessonDetailContract {

    @Immutable
    data class State(
        val phase: LessonDetailPhase = LessonDetailPhase.LessonDetailInit,
        val showReadyDialog: Boolean = false,
        val showLessonEndDialog: Boolean = false,
        val cancelReasonState: CancelReasonState = CancelReasonState(),
    )

    sealed interface LessonDetailPhase {
        data object LessonDetailInit : LessonDetailPhase
        data class LessonDetailBefore(val before: LessonDetailBeforeUiModel) : LessonDetailPhase
        data class LessonDetailOngoing(val ongoing: LessonDetailOngoingUiModel) : LessonDetailPhase
        data class LessonDetailCompleted(val completed: LessonDetailCompletedUiModel) :
            LessonDetailPhase

        data class LessonDetailCanceled(val cancel: LessonDetailCanceledUiModel) : LessonDetailPhase
    }

    sealed interface LessonDetailDialog {
        data object InstructorReady : LessonDetailDialog
        data object LessonEnd : LessonDetailDialog
    }

    sealed interface Effect {
        data object NavigateToHome : Effect
        data object NavigateToMatching : Effect
        data class ShowToast(val message: String) : Effect
    }
}