package com.ssing.presentation.instructormatching

import androidx.lifecycle.viewModelScope
import com.ssing.core.network.exception.ApiException
import com.ssing.core.ui.base.BaseViewModel
import com.ssing.core.ui.extension.uiMessage
import com.ssing.data.matching.instructormatching.repository.api.InstructorMatchingRepository
import com.ssing.presentation.instructormatching.MatchingContract.MatchingDialog
import com.ssing.presentation.instructormatching.MatchingContract.MatchingPhase
import com.ssing.presentation.instructormatching.model.DurationOption
import com.ssing.presentation.instructormatching.model.LevelOption
import com.ssing.presentation.instructormatching.model.MatchingOfferUiModel
import com.ssing.presentation.instructormatching.model.SportOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class MatchingViewModel @Inject constructor(
    private val instructorMatchingRepository: InstructorMatchingRepository,
) :
    BaseViewModel<MatchingContract.State, MatchingContract.Effect>(
        MatchingContract.State()
    ) {

    init {
        loadMatchingExposure()
    }

    private fun loadMatchingExposure() {
        viewModelScope.launch {
            instructorMatchingRepository.fetchMatchingExposure()
                .onSuccess { result ->
                    Timber.d("matching-exposure 응답: $result")
                    updateState {
                        copy(
                            exposure = exposure.applyProfile(
                                availableSports = result.availableSports.toSportOptions(),
                                resortName = result.resort.displayName,
                            ),
                        )
                    }
                }
                .onFailure {
                    Timber.e(it, "matching-exposure 실패")
                    if (it is ApiException) {
                        sendEffect(MatchingContract.Effect.ShowToast(it.uiMessage))
                    }
                }
        }
    }

    private fun List<String>.toSportOptions(): Set<SportOption> =
        mapNotNull { code ->
            when (code) {
                "SKI" -> SportOption.SKI
                "SNOWBOARD" -> SportOption.SNOWBOARD
                else -> null
            }
        }.toSet()

    fun selectSport(sport: SportOption) = updateState {
        copy(exposure = exposure.copy(selectedSports = sport))
    }

    fun toggleLevel(level: LevelOption) = updateState {
        copy(exposure = exposure.copy(selectedLevels = exposure.selectedLevels.toggle(level)))
    }

    fun toggleDuration(duration: DurationOption) = updateState {
        copy(exposure = exposure.copy(selectedDurations = exposure.selectedDurations.toggle(duration)))
    }

    fun changeMaxHeadcount(count: Int) = updateState {
        copy(exposure = exposure.copy(maxHeadcount = count))
    }

    fun changeNoticeChecked(checked: Boolean) = updateState {
        copy(exposure = exposure.copy(isNoticeChecked = checked))
    }

    fun onBack() = sendEffect(MatchingContract.Effect.NavigateBack)

    fun startMatching() {
        updateState { copy(phase = MatchingPhase.Waiting) }
    }

    fun editExposure() = updateState {
        copy(phase = MatchingPhase.SettingExposure)
    }

    fun stopWaiting() = updateState {
        copy(dialog = MatchingDialog.StopWaiting)
    }

    fun confirmStopWaiting() {
        updateState { copy(dialog = null, phase = MatchingPhase.SettingExposure) }
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
