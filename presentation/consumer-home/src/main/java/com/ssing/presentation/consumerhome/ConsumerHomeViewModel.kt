package com.ssing.presentation.consumerhome

import androidx.lifecycle.viewModelScope
import com.ssing.core.network.exception.ApiException
import com.ssing.core.ui.base.BaseViewModel
import com.ssing.core.ui.common.component.HomeLessonCardState
import com.ssing.core.ui.common.component.HomeLessonCardState.Reservation.Status
import com.ssing.core.ui.extension.uiMessage
import com.ssing.data.home.model.LessonCard
import com.ssing.data.home.repository.api.HomeRepository
import com.ssing.presentation.consumerhome.model.DisplayStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.OffsetDateTime
import javax.inject.Inject

@HiltViewModel
internal class ConsumerHomeViewModel @Inject constructor(
    private val consumerHomeRepository: HomeRepository,
) :
    BaseViewModel<ConsumerHomeContract.State, ConsumerHomeContract.Effect>(
        ConsumerHomeContract.State()
    ) {

    init {
        loadHome()
    }

    fun loadHome() {
        viewModelScope.launch {
            updateState {
                copy(isLoading = true)
            }

            consumerHomeRepository.getConsumerHome()
                .onSuccess { result ->
                    Timber.d("consumer-home 응답: $result")
                    updateState {
                        copy(
                            isLoading = false,
                            matchingPeopleCount = result.matchingPeopleCount,
                            hasUnreadNotification = result.hasUnreadNotification,
                            lessonCards = result.lessonCards.toUiState()
                        )
                    }
                }
                .onFailure { throwable ->
                    Timber.e(throwable, "consumer-home 조회 실패")

                    updateState {
                        copy(isLoading = false)
                    }

                    if (throwable is ApiException) {
                        sendEffect(ConsumerHomeContract.Effect.ShowToast(throwable.uiMessage))
                    }
                }
        }
    }

    private fun List<LessonCard>.toUiState(): ImmutableList<HomeLessonCardState> {
        if (isEmpty()) return persistentListOf(HomeLessonCardState.Empty)
        return map { it.toReservation() }.toImmutableList()
    }

    private fun LessonCard.toReservation(): HomeLessonCardState.Reservation =
        HomeLessonCardState.Reservation(
            lessonId = lessonId,
            chip = toChipText(),
            displayText = title,
            location = resort.displayName,
            date = runCatching { OffsetDateTime.parse(scheduledAt).toLocalDateTime() }.getOrNull(),
            status = toCardStatus(),
        )

    private fun LessonCard.toChipText(): String = when {
        displayStatus == DisplayStatus.IN_PROGRESS.label -> "진행중"
        remainingDays == 0 -> "Now"
        else -> "D-$remainingDays"
    }

    private fun LessonCard.toCardStatus(): Status = when {
        displayStatus == DisplayStatus.IN_PROGRESS.label -> Status.Matching
        remainingDays == 0 -> Status.Matched
        else -> Status.Default
    }

    fun onMatchingClick() {
        sendEffect(ConsumerHomeContract.Effect.NavigateToMatching)
    }

    fun onLessonClick(
        lesson: HomeLessonCardState.Reservation,
    ) {
        sendEffect(ConsumerHomeContract.Effect.NavigateToLessonDetail(lessonId = lesson.lessonId))
    }

    fun onReservationClick() {
        sendEffect(
            ConsumerHomeContract.Effect.ShowToast("준비 중인 기능이에요.")
        )
    }
}
