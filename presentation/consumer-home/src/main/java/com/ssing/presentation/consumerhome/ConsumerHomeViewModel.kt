package com.ssing.presentation.consumerhome

import androidx.lifecycle.viewModelScope
import com.ssing.core.network.exception.ApiException
import com.ssing.core.ui.R
import com.ssing.core.ui.base.BaseViewModel
import com.ssing.core.ui.common.component.Empty
import com.ssing.core.ui.common.component.HomeLessonCardState
import com.ssing.core.ui.extension.uiMessage
import com.ssing.data.home.model.ConsumerLessonCard
import com.ssing.data.home.repository.api.HomeRepository
import com.ssing.core.ui.common.component.Reservation
import com.ssing.core.ui.common.component.Reservation.Status
import com.ssing.data.matching.consumermatching.repository.api.ConsumerMatchingRepository
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
    private val consumerMatchingRepository: ConsumerMatchingRepository,
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

    private fun List<ConsumerLessonCard>.toUiState(): ImmutableList<HomeLessonCardState> {
        if (isEmpty()) return persistentListOf(Empty)
        return mapNotNull { card ->
            runCatching {
                card.toReservation()
            }.onFailure { throwable ->
                Timber.e(throwable, "${card.lessonId} 조회 실패")
            }.getOrNull()
        }.toImmutableList()
    }

    private fun ConsumerLessonCard.toReservation(): Reservation =
        Reservation(
            lessonId = lessonId,
            chip = toChipText(),
            displayText = title,
            location = resort.displayName,
            date = runCatching { OffsetDateTime.parse(scheduledAt).toLocalDateTime() }.getOrNull(),
            imageRes = toImageRes(),
            status = toCardStatus(),
        )

    private fun ConsumerLessonCard.toImageRes(): Int = when (sport) {
        "SKI" -> R.drawable.img_ski_86
        "SNOWBOARD " -> R.drawable.img_snowboard_86
        else -> R.drawable.img_ski_86
    }

    private fun ConsumerLessonCard.toChipText(): String = when {
        displayStatus == IN_PROGRESS -> "진행중"
        remainingDays == 0 -> "Now"
        else -> "D-$remainingDays"
    }

    private fun ConsumerLessonCard.toCardStatus(): Status = when {
        displayStatus == IN_PROGRESS -> Status.Matching
        remainingDays == 0 -> Status.Matched
        else -> Status.Default
    }

    private companion object {
        const val IN_PROGRESS = "IN_PROGRESS"
    }

    fun onMatchingClick() = viewModelScope.launch {
        consumerMatchingRepository.getMatchingActive()
            .onSuccess { matchingRequestId ->
                if (matchingRequestId != null) {
                    Timber.d("씽 매칭 클릭-진행 중 매칭 있음-바로 매칭으로")
                    sendEffect(
                        ConsumerHomeContract.Effect.NavigateToActiveMatching(matchingRequestId)
                    )
                    sendEffect(ConsumerHomeContract.Effect.ShowToast("진행 중인 매칭이 있어요."))
                } else {
                    Timber.d("씽 매칭 클릭-진행 중 매칭 없음-매칭 조건 입력으로")
                    sendEffect(ConsumerHomeContract.Effect.NavigateToMatching)
                }
            }
            .onFailure {
                Timber.e(it, "씽 매칭 클릭-매칭 상태 조회 실패-매칭 조건 입력으로")
                sendEffect(ConsumerHomeContract.Effect.NavigateToMatching)
            }
    }

    fun onLessonClick(
        lesson: Reservation,
    ) {
        sendEffect(ConsumerHomeContract.Effect.NavigateToLessonDetail(lessonId = lesson.lessonId))
    }

    fun onReservationClick() {
        sendEffect(
            ConsumerHomeContract.Effect.ShowToast("준비 중인 기능이에요.")
        )
    }
}
