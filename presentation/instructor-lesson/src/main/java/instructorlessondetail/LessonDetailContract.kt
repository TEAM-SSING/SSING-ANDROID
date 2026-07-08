package instructorlessondetail

import androidx.compose.runtime.Immutable
import instructorlessondetail.model.LessonDetailAfterUiModel
import instructorlessondetail.model.LessonDetailBeforeUiModel
import instructorlessondetail.model.LessonDetailCanceledUiModel
import instructorlessondetail.model.LessonDetailDuringUiModel

internal interface LessonDetailContract {

    @Immutable
    data class State(
        val phase: LessonDetailPhase = LessonDetailPhase.Loading,
        val dialog: LessonDetailDialog? = null,
    )

    sealed interface LessonDetailPhase {
        data object Loading : LessonDetailPhase
        data class LessonDetailBefore(val before: LessonDetailBeforeUiModel) : LessonDetailPhase
        data class LessonDetailDuring(val during: LessonDetailDuringUiModel) : LessonDetailPhase
        data class LessonDetailAfter(val after: LessonDetailAfterUiModel) : LessonDetailPhase
        data class LessonDetailCanceled(val cancel: LessonDetailCanceledUiModel) : LessonDetailPhase
    }

    sealed interface LessonDetailDialog {
        data object Chatting : LessonDetailDialog
        data object InstructorReady : LessonDetailDialog
        data object LessonCanceled : LessonDetailDialog
        data object ReportIssue : LessonDetailDialog
        data object LessonEnd : LessonDetailDialog
        data object Review : LessonDetailDialog
        data object ViewLessonHistory : LessonDetailDialog
        data object ViewEarnings : LessonDetailDialog
        data object BackToMatching : LessonDetailDialog
    }

    sealed interface Effect {
        data object NavigateBack : Effect
    }
}