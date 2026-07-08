package instructorlessondetail

import androidx.compose.runtime.Immutable
import instructorlessondetail.screen.TeamParticipantsInfo
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal interface LessonDetailContract {

    @Immutable
    data class State(
        val phase: LessonDetailPhase = LessonDetailPhase.settingCondition,
        val isLoading: Boolean = false,
        val isRequesting: Boolean = false,
        val instructorId: Long = 0L,
        val instructorName: String = "",
        val profileImageUrl: String = "",
        val description: String = "",

        val isInstructorReady: Boolean = false,
        val participantReadyCount: Int = 0,
        val participantTotalCount: Int = 0,
        val tags: ImmutableList<String> = persistentListOf(),
        val classTitle: String = "",
        val location: String = "",
        val duration: String = "",
        val price: Int = 0,
        val teams: ImmutableList<TeamParticipantsInfo> = persistentListOf(),
    )

    sealed interface LessonDetailPhase {
        data object settingCondition : LessonDetailPhase
        data object matching : LessonDetailPhase
        data object lessonInProgress : LessonDetailPhase
    }

    sealed interface LessonDetailDialog {
        data object StopWaiting : LessonDetailDialog
        data object ConsumerRejected : LessonDetailDialog
        data object LessonCanceled : LessonDetailDialog
    }

    sealed interface Effect {
        data object NavigateBack : Effect
        data class ShowToast(val message: String) : Effect

        data object ShowCancelClassDialog : Effect
    }
}