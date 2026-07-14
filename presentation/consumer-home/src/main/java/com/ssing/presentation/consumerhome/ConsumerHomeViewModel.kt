package com.ssing.presentation.consumerhome

import androidx.lifecycle.viewModelScope
import com.ssing.core.ui.base.BaseViewModel
import com.ssing.core.ui.common.component.HomeLessonCardState
import com.ssing.core.ui.common.component.HomeLessonCardState.Reservation.Status
import com.ssing.data.consumerhome.model.DisplayStatus
import com.ssing.data.consumerhome.model.LessonCards
import com.ssing.data.consumerhome.repository.api.ConsumerHomeRepository
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
    private val consumerHomeRepository: ConsumerHomeRepository,
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

                    sendEffect(
                        ConsumerHomeContract.Effect.ShowToast(
                            "홈 정보를 불러오지 못했어요.",
                        ),
                    )
                }
        }
    }

    private fun List<LessonCards>.toUiState(): ImmutableList<HomeLessonCardState> {
        if (isEmpty()) return persistentListOf(HomeLessonCardState.Empty)
        return map { it.toReservation() }.toImmutableList()
    }

    private fun LessonCards.toReservation(): HomeLessonCardState.Reservation =
        HomeLessonCardState.Reservation(
            lessonId = lessonId,
            chip = toChipText(),
            displayText = title,
            location = resort.displayName,
            date = OffsetDateTime.parse(scheduledAt).toLocalDateTime(),
            status = toCardStatus(),
        )

    private fun LessonCards.toChipText(): String = when {
        displayStatus == DisplayStatus.IN_PROGRESS -> "진행중"
        remainingDays == 0 -> "Now"
        else -> "D-$remainingDays"
    }

    private fun LessonCards.toCardStatus(): Status = when (displayStatus) {
        DisplayStatus.IN_PROGRESS -> Status.Matching
        DisplayStatus.CONFIRMED -> Status.Default
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
