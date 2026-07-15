package com.ssing.presentation.instructormatching

import androidx.compose.runtime.Immutable
import com.ssing.presentation.instructormatching.model.MatchingExposureUiState
import com.ssing.presentation.instructormatching.model.MatchingOfferUiModel
import com.ssing.presentation.instructormatching.model.MatchingWaitingUiState

internal interface MatchingContract {

    @Immutable
    data class State(
        val phase: MatchingPhase = MatchingPhase.SettingExposure,
        val exposure: MatchingExposureUiState = MatchingExposureUiState(),
        val waiting: MatchingWaitingUiState = MatchingWaitingUiState(),
        val dialog: MatchingDialog? = null,
    )

    sealed interface MatchingPhase {
        data object SettingExposure : MatchingPhase

        data object Waiting : MatchingPhase

        data class OfferArrived(val offer: MatchingOfferUiModel) : MatchingPhase

        data class PendingConfirm(
            val offer: MatchingOfferUiModel,
            val confirmationExpiresAtMillis: Long? = null,
        ) : MatchingPhase
    }

    sealed interface MatchingDialog {
        data object StopWaiting : MatchingDialog
    }

    sealed interface Effect {
        data class ShowToast(val message: String) : Effect
        data object NavigateBack : Effect
        data class NavigateToLessonDetail(val lessonId: Long) : Effect
    }
}