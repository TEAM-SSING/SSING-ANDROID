package instructorlessondetail

import com.ssing.core.ui.base.BaseViewModel
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
            copy(dialog = LessonDetailContract.LessonDetailDialog.InstructorReady)
        }
    }

    fun onReadyDialogConfirm() {
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

    fun onEndButtonClick() {
        updateState {
            copy(dialog = LessonDetailContract.LessonDetailDialog.LessonEnd)
        }
    }

    fun onEndDialogConfirm() {
        updateState {
            copy(dialog = null)
        }
    }

    fun onDialogDismiss() {
        updateState {
            copy(dialog = null)
        }
    }
}
