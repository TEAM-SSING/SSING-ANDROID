package com.ssing.presentation.instructorhome

import androidx.lifecycle.viewModelScope
import com.ssing.core.network.exception.ApiException
import com.ssing.core.ui.R
import com.ssing.core.ui.base.BaseViewModel
import com.ssing.core.ui.common.component.Empty
import com.ssing.core.ui.common.component.HomeLessonCardState
import com.ssing.core.ui.common.component.Reservation
import com.ssing.core.ui.common.component.Reservation.Status
import com.ssing.core.ui.extension.uiMessage
import com.ssing.data.home.model.InstructorLessonCard
import com.ssing.data.home.repository.api.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.OffsetDateTime
import javax.inject.Inject
import kotlin.collections.mapNotNull

@HiltViewModel
internal class InstructorHomeViewModel @Inject constructor(
    private val instructorHomeRepository: HomeRepository,
) :
    BaseViewModel<InstructorHomeContract.State, InstructorHomeContract.Effect>(
        InstructorHomeContract.State()
    ) {

    fun loadHome() {
        viewModelScope.launch {
            updateState {
                copy(isLoading = true)
            }

            instructorHomeRepository.getInstructorHome()
                .onSuccess { result ->
                    Timber.d("instructor-home 응답: $result")

                    updateState {
                        copy(
                            isLoading = false,
                            lessonCards = result.lessonCards.toUiState(),
                            hasActiveLesson = result.lessonCards.any {
                                it.displayStatus == CONFIRMED || it.displayStatus == IN_PROGRESS
                            },
                            hasUnreadNotification = result.hasUnreadNotification,
                            instructorName = result.instructorName,
                            matchingPeopleCount = result.matchingPeopleCount,
                            averageRating = 4.0f,
                            grade = Grade.GRADE4,
                            achievementRate = 77,
                        )
                    }
                }
                .onFailure { throwable ->
                    Timber.e(throwable, "instructor-home 조회 실패")

                    updateState {
                        copy(isLoading = false)
                    }

                    if (throwable is ApiException) {
                        sendEffect(InstructorHomeContract.Effect.ShowToast(throwable.uiMessage))
                    }
                }
        }
    }

    private fun List<InstructorLessonCard>.toUiState(): ImmutableList<HomeLessonCardState> {
        if (isEmpty()) return persistentListOf(Empty)
        return mapNotNull { card ->
            runCatching {
                card.toReservation()
            }.onFailure { throwable ->
                Timber.e(throwable, "${card.lessonId} 조회 실패")
            }.getOrNull()
        }.toImmutableList()
    }

    private fun InstructorLessonCard.toReservation(): Reservation {
        val status = toReservationStatus()
        return Reservation(
            lessonId = lessonId,
            offerId = offerId,
            chip = toChipText(status),
            displayText = title,
            location = resort.displayName,
            date = runCatching { OffsetDateTime.parse(scheduledAt).toLocalDateTime() }.getOrNull(),
            imageRes = toImageRes(),
            status = status,
        )
    }

    private fun InstructorLessonCard.toImageRes(): Int = when (sport) {
        "SKI" -> R.drawable.img_ski_86
        "SNOWBOARD" -> R.drawable.img_snowboard_86
        else -> R.drawable.img_ski_86
    }

    private fun InstructorLessonCard.toChipText(status: Status): String =
        if (status == Status.Default) "D-$remainingDays" else "Now"

    private fun InstructorLessonCard.toReservationStatus(): Status =
        when (displayStatus) {
            MATCHING -> Status.Matching

            WAITING_FOR_INSTRUCTOR,
            WAITING_FOR_CONFIRMATION,
            PAYMENT_PENDING -> Status.Matched

            IN_PROGRESS -> Status.Matching

            CONFIRMED -> Status.Default

            else -> Status.Default
        }

    private companion object {
        const val MATCHING = "MATCHING"
        const val WAITING_FOR_CONFIRMATION = "WAITING_FOR_CONFIRMATION"
        const val WAITING_FOR_INSTRUCTOR = "WAITING_FOR_INSTRUCTOR"
        const val PAYMENT_PENDING = "PAYMENT_PENDING"
        const val CONFIRMED = "CONFIRMED"
        const val IN_PROGRESS = "IN_PROGRESS"

        const val MSG_ACTIVE_LESSON_BLOCK =
            "진행 예정이거나 진행 중인 강습이 있어 즉시매칭을 시작할 수 없어요."
    }

    fun onMatchingClick() {
        // 확정/진행 중인 강습이 있으면 즉시매칭 시작을 막는다.
        if (uiState.value.hasActiveLesson) {
            sendEffect(InstructorHomeContract.Effect.ShowToast(MSG_ACTIVE_LESSON_BLOCK))
            return
        }
        sendEffect(InstructorHomeContract.Effect.NavigateToMatching)
    }

    fun onLessonClick(lesson: Reservation) {
        Timber.i("lessonId: ${lesson.lessonId} / offerId: ${lesson.offerId}")

        when {
            lesson.lessonId != null -> sendEffect(
                InstructorHomeContract.Effect.NavigateToLessonDetail(lessonId = lesson.lessonId)
            )
            lesson.offerId != null -> sendEffect(
                InstructorHomeContract.Effect.NavigateToMatchingWaiting(offerId = lesson.offerId)
            )
            else -> sendEffect(
                InstructorHomeContract.Effect.NavigateToMatchingWaiting(offerId = null)
            )
        }
    }

    fun onReviewClick() {
        sendEffect(
            InstructorHomeContract.Effect.ShowToast("준비 중인 기능이에요.")
        )
    }

    fun onReservationClick() {
        sendEffect(
            InstructorHomeContract.Effect.ShowToast("준비 중인 기능이에요.")
        )
    }
}
