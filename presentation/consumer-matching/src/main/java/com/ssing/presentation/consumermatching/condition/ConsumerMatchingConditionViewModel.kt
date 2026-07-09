package com.ssing.presentation.consumermatching.condition

import com.ssing.core.ui.base.BaseViewModel
import com.ssing.presentation.consumermatching.type.ConsumerGender
import kotlinx.collections.immutable.toPersistentList

internal class ConsumerMatchingConditionViewModel :
    BaseViewModel<ConsumerMatchingConditionContract.State, ConsumerMatchingConditionContract.Effect>(
        initialState = ConsumerMatchingConditionContract.State(),
    ) {
    private var nextConsumerId = 1

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

    fun onAddConsumerClick() {
        updateState {
            copy(consumers = consumers.add(ConsumerInfo(id = nextConsumerId++)))
        }
    }

    fun onConsumerDelete(id: Int) {
        if (uiState.value.consumers.size == 1) return
        updateState { copy(consumers = consumers.removeAll { it.id == id }) }
    }

    fun onConsumerGenderSelect(id: Int, gender: ConsumerGender) =
        updateState {
            copy(
                consumers = consumers.map {
                    if (it.id == id) it.copy(gender = gender) else it
                }.toPersistentList(),
            )
        }

    fun onConsumerFocusChange(id: Int, isFocused: Boolean) =
        updateState {
            copy(
                consumers = consumers.map {
                    it.copy(isFocused = it.id == id && isFocused)
                }.toPersistentList(),
            )
        }

    fun onStartMatchingClick() {
        sendEffect(ConsumerMatchingConditionContract.Effect.NavigateToMatching)
    }
}
