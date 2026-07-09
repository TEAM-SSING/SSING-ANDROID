package com.ssing.presentation.consumerlesson

import androidx.compose.runtime.Immutable
import com.ssing.core.ui.common.component.LessonBannerState



internal interface ConsumerLessonContract {

    @Immutable
    data class State(
        val lessonBannerState: LessonBannerState = LessonBannerState.Before(
            isInstructorReady = false,
            participantTotalCount = 0,
            participantReadyCount = 0,
        ),
        val isReady: Boolean = false,
        val showCancelConfirmSheet: Boolean = false,
    )

    sealed interface Effect {
        data class ShowToast(val message: String) : Effect
        data object NavigationToHome : Effect
    }

}