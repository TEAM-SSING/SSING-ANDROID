package com.ssing.presentation.consumerlesson

import androidx.compose.runtime.Immutable
import com.ssing.core.ui.common.component.CancelReason
import com.ssing.core.ui.common.component.LessonBannerState
import com.ssing.presentation.consumerlesson.model.CanceledLessonInfoUiModel
import com.ssing.presentation.consumerlesson.model.CompletedLessonInfoUiModel
import com.ssing.presentation.consumerlesson.model.InstructorProfileUiModel
import com.ssing.presentation.consumerlesson.model.LessonInfoUiModel
import com.ssing.presentation.consumerlesson.model.ParticipantTeamUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf


internal interface ConsumerLessonContract {

    @Immutable
    data class State(
        val lessonBannerState: LessonBannerState = LessonBannerState.Before(
            isInstructorReady = false,
            participantTotalCount = 0,
            participantReadyCount = 0,
        ),

        val lessonInfo: LessonInfoUiModel? = null,
        val completedLessonInfo: CompletedLessonInfoUiModel? = null,
        val canceledLessonInfo: CanceledLessonInfoUiModel? = null,
        val instructorProfile: InstructorProfileUiModel? = null,
        val participantTeams: ImmutableList<ParticipantTeamUiModel> = persistentListOf(),

        val isReady: Boolean = false,
        val showReadyAlert: Boolean = false,
        val showEndLessonAlert: Boolean = false,
        val showCancelConfirmSheet: Boolean = false,
        val selectedReason: CancelReason? = null,
        val etcReason: String? = null,
    )

    sealed interface Effect {
        data class ShowToast(val message: String) : Effect
        data object NavigationToHome : Effect
    }

}