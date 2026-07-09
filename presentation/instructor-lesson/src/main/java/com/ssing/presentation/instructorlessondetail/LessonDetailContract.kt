package com.ssing.presentation.instructorlessondetail

import androidx.compose.runtime.Immutable
import com.ssing.presentation.instructorlessondetail.model.LessonDetailBeforeUiModel
import com.ssing.presentation.instructorlessondetail.model.LessonDetailCanceledUiModel
import com.ssing.presentation.instructorlessondetail.model.LessonDetailCompletedUiModel
import com.ssing.presentation.instructorlessondetail.model.LessonDetailOngoingUiModel

internal interface LessonDetailContract {

    @Immutable
    data class State(
        val phase: LessonDetailPhase = LessonDetailPhase.Loading,
        val dialog: LessonDetailDialog? = null,
        val showReadyDialog:Boolean=false,
    )

    sealed interface LessonDetailPhase {
        data object Loading : LessonDetailPhase
        data class LessonDetailBefore(val before: LessonDetailBeforeUiModel) : LessonDetailPhase
        data class LessonDetailOngoing(val ongoing: LessonDetailOngoingUiModel) : LessonDetailPhase
        data class LessonDetailCompleted(val completed: LessonDetailCompletedUiModel) : LessonDetailPhase
        data class LessonDetailCanceled(val cancel: LessonDetailCanceledUiModel) : LessonDetailPhase
    }

    sealed interface LessonDetailDialog {
        data object InstructorReady : LessonDetailDialog
        data object LessonEnd : LessonDetailDialog
    }

    sealed interface Effect {
        data object NavigateBack : Effect
    }
}