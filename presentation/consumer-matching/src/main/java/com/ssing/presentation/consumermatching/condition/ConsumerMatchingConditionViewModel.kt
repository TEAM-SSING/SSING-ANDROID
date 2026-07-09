package com.ssing.presentation.consumermatching.condition

import com.ssing.core.ui.base.BaseViewModel

internal class ConsumerMatchingConditionViewModel :
    BaseViewModel<ConsumerMatchingConditionContract.State, ConsumerMatchingConditionContract.Effect>(
        initialState = ConsumerMatchingConditionContract.State(),
    ) {
    fun onResortSelect(resort: Resort) =
        updateState { copy(selectedResort = resort) }

    fun onSportSelect(sport: Sport) =
        updateState { copy(selectedSport = sport) }

    fun onLevelSelect(level: LessonLevel) =
        updateState { copy(selectedLevel = level) }

    fun onDurationSelect(duration: LessonDuration) {
        val currentDurations = uiState.value.selectedDurations
        val newDurations = if (duration in currentDurations) {
            currentDurations - duration
        } else {
            currentDurations + duration
        }
        updateState { copy(selectedDurations = newDurations) }
    }

    fun onConfirm(isChecked: Boolean) =
        updateState { copy(isConfirmed = isChecked) }

    fun onStartMatchingClick() {
        sendEffect(ConsumerMatchingConditionContract.Effect.NavigateToMatching)
    }
}
