package com.ssing.presentation.consumermatching.condition

import androidx.lifecycle.viewModelScope
import com.ssing.core.network.exception.ApiException
import com.ssing.core.ui.base.BaseViewModel
import com.ssing.core.ui.extension.uiMessage
import com.ssing.data.consumermatching.model.ConsumerMatchingParticipant
import com.ssing.data.consumermatching.repository.api.ConsumerMatchingRepository
import com.ssing.presentation.consumermatching.type.ConsumerGender
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ConsumerMatchingConditionViewModel @Inject constructor(
    private val consumerMatchingRepository: ConsumerMatchingRepository,
) :
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

    fun onConsumerDelete(id: Int) =
        updateState { copy(consumers = consumers.removeAll { it.id == id }) }

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
        val hasInvalidAge = uiState.value.consumers.any { consumer ->
            consumer.ageState.text.toString().toIntOrNull()?.let { age -> age > 200 } ?: true
        }
        if (hasInvalidAge) {
            return sendEffect(ConsumerMatchingConditionContract.Effect.ShowToast("200세 이하로 입력해주세요."))
        }

        updateState { copy(isLoading = true) }

        viewModelScope.launch {
            consumerMatchingRepository.requestMatching(
                resort = uiState.value.selectedResort?.api ?: "",
                sport = uiState.value.selectedSport?.api ?: "",
                lessonLevel = uiState.value.selectedLevel?.api ?: "",
                requestedDurationMinutes = uiState.value.selectedDurations.map { it.api },
                participants = uiState.value.consumers.map { it.toParticipant() },
                equipmentReady = uiState.value.isConfirmed,
            )
                .onSuccess {
                    sendEffect(ConsumerMatchingConditionContract.Effect.NavigateToMatching)
                    updateState { copy(isLoading = false) }
                }
                .onFailure {
                    if (it is ApiException) {
                        sendEffect(ConsumerMatchingConditionContract.Effect.ShowToast(it.uiMessage))
                    }
                    updateState { copy(isLoading = false) }
                }
        }
    }

    private fun ConsumerInfo.toParticipant(): ConsumerMatchingParticipant =
        ConsumerMatchingParticipant(
            age = this.ageState.text.toString().toIntOrNull() ?: 0,
            gender = this.gender?.api ?: "",
        )
}
