package com.ssing.presentation.consumerlesson

import androidx.compose.runtime.Immutable
import com.ssing.core.ui.common.component.CancelReason
import com.ssing.core.ui.common.component.LessonBannerState
import com.ssing.core.ui.common.component.SsingButtonStyle
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
    ) {
        val isBeforeAndReady: Boolean = lessonBannerState is LessonBannerState.Before && isReady

        val bottomButtonText: String = when (lessonBannerState) {
            is LessonBannerState.Before -> if (isReady) {
                "강습 대기 중"
            } else {
                "강습 준비 완료"
            }

            is LessonBannerState.Ongoing -> "강습 종료"
            is LessonBannerState.Completed -> "리뷰 쓰기"
            is LessonBannerState.Canceled -> "홈으로 돌아가기"
        }

        val bottomButtonEnabled: Boolean = !isBeforeAndReady

        val bottomButtonStyle: SsingButtonStyle =
            if (isBeforeAndReady) SsingButtonStyle.GRAY else SsingButtonStyle.BLUE
    }

    sealed interface Effect {
        data class ShowToast(val message: String) : Effect
        data object NavigationToHome : Effect
    }

}