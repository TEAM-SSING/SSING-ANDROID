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
import com.ssing.data.home.model.LessonCard
import com.ssing.data.home.repository.api.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.OffsetDateTime
import javax.inject.Inject

@HiltViewModel
internal class InstructorHomeViewModel @Inject constructor(
    private val instructorHomeRepository: HomeRepository,
) :
    BaseViewModel<InstructorHomeContract.State, InstructorHomeContract.Effect>(
        InstructorHomeContract.State()
    ) {

    init{
        loadHome()
    }

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
                            hasUnreadNotification = result.hasUnreadNotification,
                            nickname = result.nickname,
                            matchingPeopleCount = result.matchingPeopleCount,
                            averageRating = result.reviewSummary.averageRating,
                            grade = result.reviewSummary.grade.toGrade(),
                            achievementRate = result.reviewSummary.achievementRate,
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

    private fun List<LessonCard>.toUiState(): ImmutableList<HomeLessonCardState> {
        if (isEmpty()) return persistentListOf(Empty)
        return mapNotNull { card ->
            runCatching {
                card.toReservation()
            }.onFailure { throwable ->
                Timber.e(throwable, "${card.lessonId} 조회 실패")
            }.getOrNull()
        }.toImmutableList()
    }

    private fun LessonCard.toReservation(): Reservation =
        Reservation(
            lessonId = lessonId,
            chip = toChipText(),
            displayText = title,
            location = resort.displayName,
            date = runCatching { OffsetDateTime.parse(scheduledAt).toLocalDateTime() }.getOrNull(),
            imageRes = toImageRes(),
            status = toCardStatus(),
        )

    private fun LessonCard.toImageRes(): Int = when (sport) {
        "SKI" -> R.drawable.img_ski_86
        "SNOWBOARD "-> R.drawable.img_snowboard_86
        else -> R.drawable.img_ski_86
    }

    private fun LessonCard.toChipText(): String = when {
        displayStatus == IN_PROGRESS -> "진행중"
        remainingDays == 0 -> "Now"
        else -> "D-$remainingDays"
    }

    private fun LessonCard.toCardStatus(): Status = when {
        displayStatus == IN_PROGRESS -> Status.Matching
        remainingDays == 0 -> Status.Matched
        else -> Status.Default
    }

    private fun Int.toGrade(): Grade =
        when (this) {
            1 -> Grade.GRADE1
            2 -> Grade.GRADE2
            3 -> Grade.GRADE3
            4 -> Grade.GRADE4
            5 -> Grade.GRADE5
            else -> Grade.GRADE1
        }

    private companion object {
        const val IN_PROGRESS = "IN_PROGRESS"
    }

    fun onMatchingClick() {
        sendEffect(InstructorHomeContract.Effect.NavigateToMatching)

    }

    fun onLessonClick(
        lesson: Reservation,
    ) {
        sendEffect(InstructorHomeContract.Effect.NavigateToLessonDetail(lessonId = lesson.lessonId))
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
