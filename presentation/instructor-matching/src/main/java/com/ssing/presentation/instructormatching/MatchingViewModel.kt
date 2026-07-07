package com.ssing.presentation.instructormatching

import com.ssing.core.ui.base.BaseViewModel
import com.ssing.presentation.instructormatching.MatchingContract.MatchingDialog
import com.ssing.presentation.instructormatching.MatchingContract.MatchingPhase
import com.ssing.presentation.instructormatching.model.DurationOption
import com.ssing.presentation.instructormatching.model.LevelOption
import com.ssing.presentation.instructormatching.model.MatchingOfferUiModel
import com.ssing.presentation.instructormatching.model.SportOption
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class MatchingViewModel @Inject constructor() :
    BaseViewModel<MatchingContract.State, MatchingContract.Effect>(
        MatchingContract.State()
    ) {

    init {
        updateState {
            copy(
                condition = condition.applyProfile(
                    availableSports = setOf(SportOption.SKI),
                    resortName = "하이원 리조트",
                ),
            )
        }
    }

    fun toggleSport(sport: SportOption) = updateState {
        if (condition.availableSports.size <= 1) this
        else copy(condition = condition.copy(selectedSports = condition.selectedSports.toggle(sport)))
    }

    fun toggleLevel(level: LevelOption) = updateState {
        copy(condition = condition.copy(selectedLevels = condition.selectedLevels.toggle(level)))
    }

    fun toggleDuration(duration: DurationOption) = updateState {
        copy(condition = condition.copy(selectedDurations = condition.selectedDurations.toggle(duration)))
    }

    fun changeMaxHeadcount(count: Int) = updateState {
        copy(condition = condition.copy(maxHeadcount = count))
    }

    fun changeNoticeChecked(checked: Boolean) = updateState {
        copy(condition = condition.copy(isNoticeChecked = checked))
    }

    fun onBack() = sendEffect(MatchingContract.Effect.NavigateBack)

    fun startMatching() {
        updateState { copy(phase = MatchingPhase.Waiting) }
    }

    fun editCondition() = updateState {
        copy(phase = MatchingPhase.SettingCondition)
    }

    fun stopWaiting() = updateState {
        copy(dialog = MatchingDialog.StopWaiting)
    }

    fun confirmStopWaiting() {
        updateState { copy(dialog = null, phase = MatchingPhase.SettingCondition) }
    }

    fun acceptOffer() {
        val offer = currentOffer() ?: return
        updateState {
            copy(phase = MatchingPhase.PendingConfirm(offer = offer, confirmationExpiresAtMillis = null))
        }
    }

    fun rejectOffer() {
        updateState { copy(phase = MatchingPhase.Waiting) }
    }

    fun continueMatching() = updateState {
        copy(dialog = null, phase = MatchingPhase.Waiting)
    }

    fun retryMatching() {
        updateState { copy(dialog = null) }
    }

    fun dismissDialog() = updateState { copy(dialog = null) }

    private fun currentOffer(): MatchingOfferUiModel? =
        when (val phase = uiState.value.phase) {
            is MatchingPhase.OfferArrived -> phase.offer
            is MatchingPhase.PendingConfirm -> phase.offer
            else -> null
        }

    private fun <T> Set<T>.toggle(item: T): Set<T> =
        if (item in this) this - item else this + item
}
