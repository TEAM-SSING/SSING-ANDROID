package com.ssing.presentation.instructorlessondetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ssing.core.network.exception.ApiException
import com.ssing.core.ui.base.BaseViewModel
import com.ssing.core.ui.common.component.CancelReason
import com.ssing.core.ui.extension.uiMessage
import com.ssing.core.ui.util.ssingDateFormatter
import com.ssing.data.lesson.instructorlesson.repository.api.InstructorLessonRepository
import com.ssing.data.lesson.model.InstructorLessonDetailBefore
import com.ssing.data.lesson.model.InstructorLessonDetailCanceled
import com.ssing.data.lesson.model.InstructorLessonDetailCompleted
import com.ssing.data.lesson.model.InstructorLessonDetailOngoing
import com.ssing.data.lesson.model.InstructorLessonDetailRequestResult
import com.ssing.data.lesson.model.MatchingRequest
import com.ssing.data.lesson.repository.api.LessonRepository
import com.ssing.presentation.instructorlessondetail.model.LessonDetailBeforeUiModel
import com.ssing.presentation.instructorlessondetail.model.LessonDetailCanceledUiModel
import com.ssing.presentation.instructorlessondetail.model.LessonDetailCompletedUiModel
import com.ssing.presentation.instructorlessondetail.model.LessonDetailOngoingUiModel
import com.ssing.presentation.instructorlessondetail.model.TeamParticipantsInfo
import com.ssing.presentation.instructorlessondetail.navigation.InstructorLesson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
internal class LessonDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val lessonRepository: LessonRepository,
    private val instructorLessonDetailRepository: InstructorLessonRepository,
) :
    BaseViewModel<LessonDetailContract.State, LessonDetailContract.Effect>(
        LessonDetailContract.State()
    ) {

    private val lessonId = savedStateHandle.toRoute<InstructorLesson>().lessonId

    init {
        loadLessonDetail()
    }

    private fun loadLessonDetail() {
        updateState { copy(phase = LessonDetailContract.LessonDetailPhase.Loading) }

        viewModelScope.launch {
            instructorLessonDetailRepository.instructorLessonDetail(lessonId)
                .onSuccess { result ->
                    updateState { copy(phase = result.toPhase()) }
                }
                .onFailure {
                    if (it is ApiException) {
                        sendEffect(LessonDetailContract.Effect.ShowToast(it.uiMessage))
                    }
                    sendEffect(LessonDetailContract.Effect.NavigateBack)
                }
        }
    }

    fun onBackClick() = sendEffect(LessonDetailContract.Effect.NavigateBack)

    fun onCancelClassClick() {
        updateState {
            copy(showReadyDialog = false, showLessonEndDialog = false)
        }
    }

    fun onReadyButtonClick() {
        updateState {
            copy(showReadyDialog = true)
        }
    }

    fun onReadyDialogDismiss() {
        updateState {
            copy(showReadyDialog = false)
        }
    }

    fun onEndClick() {
        updateState {
            copy(showLessonEndDialog = true)
        }
    }

    fun onLessonEndDialogDismiss() {
        updateState {
            copy(showLessonEndDialog = false)
        }
    }

    fun onChatRoomClick() {
        updateState {
            copy(showLessonEndDialog = false)
        }
    }

    fun onReadyClick() {
        val before =
            (uiState.value.phase as? LessonDetailContract.LessonDetailPhase.LessonDetailBefore)
                ?.before

        if (before == null) {
            loadLessonDetail()
            return
        }

        updateState {
            copy(
                phase = LessonDetailContract.LessonDetailPhase.LessonDetailBefore(
                    before = before.copy(isInstructorReady = true)
                ),
                showReadyDialog = false,
            )
        }
        viewModelScope.launch {
            lessonRepository.lessonStart(before.lessonId)
                .onFailure {
                    updateState {
                        copy(
                            phase = LessonDetailContract.LessonDetailPhase.LessonDetailBefore(
                                before = before.copy(isInstructorReady = false)
                            ),
                        )
                    }
                    if (it is ApiException) {
                        sendEffect(LessonDetailContract.Effect.ShowToast(it.uiMessage))
                    }
                }
        }
    }

    fun onContinueClick() {
        updateState {
            copy(showLessonEndDialog = false)
        }
    }

    fun onCancelReasonSelect(reason: CancelReason) {
        updateState {
            copy(cancelReasonState = cancelReasonState.copy(selectedReason = reason))
        }
    }

    fun onCancelSheetOpen() {
        updateState {
            copy(cancelReasonState = cancelReasonState.copy(visible = true))
        }
    }

    fun onCancelSheetDismiss() {
        updateState {
            copy(
                cancelReasonState = cancelReasonState.copy(
                    visible = false,
                    selectedReason = null
                )
            )
        }
    }

    fun onCancelConfirmClick(customReason: String?) {
        val reason = uiState.value.cancelReasonState.selectedReason ?: return
        updateState {
            copy(
                cancelReasonState = cancelReasonState.copy(
                    visible = false,
                    selectedReason = null
                )
            )
        }
    }
}

private fun InstructorLessonDetailRequestResult.toPhase(): LessonDetailContract.LessonDetailPhase =
    when (this) {
        is InstructorLessonDetailBefore -> LessonDetailContract.LessonDetailPhase.LessonDetailBefore(
            before = LessonDetailBeforeUiModel(
                lessonId = lessonId,
                isInstructorReady = instructorConfirmed,
                participantReadyCount = confirmedCount,
                participantTotalCount = requiredCount,
                tags = listOf(sport, lessonLevel).toPersistentList(),
                classTitle = representativeConsumerNames.joinToString(),
                location = resortDisplayName,
                duration = scheduledDurationMinutes.toDurationText(),
                price = totalLessonPrice,
                teams = matchingRequests.map { it.toModel() }.toPersistentList(),
            )
        )

        is InstructorLessonDetailOngoing -> LessonDetailContract.LessonDetailPhase.LessonDetailOngoing(
            ongoing = LessonDetailOngoingUiModel(
                tags = listOf(sport, lessonLevel).toPersistentList(),
                classTitle = representativeConsumerNames.joinToString(),
                remainingTime = remainingSeconds.toTimeText(),
                elapsedTime = elapsedSeconds.toTimeText(),
                location = resortDisplayName,
                duration = scheduledDurationMinutes.toDurationText(),
                price = totalLessonPrice,
                teams = matchingRequests.map { it.toModel() }.toPersistentList(),
            )
        )

        is InstructorLessonDetailCompleted -> {
            val startedAt = runCatching { LocalDateTime.parse(actualStartedAt) }.getOrNull()
            val endedAt = runCatching { LocalDateTime.parse(actualEndedAt) }.getOrNull()
            LessonDetailContract.LessonDetailPhase.LessonDetailCompleted(
                completed = LessonDetailCompletedUiModel(
                    tags = listOf(sport, lessonLevel).toPersistentList(),
                    classTitle = representativeConsumerNames.joinToString(),
                    lessonDate = startedAt?.ssingDateFormatter() ?: "",
                    lessonTime = if (startedAt != null && endedAt != null) "${startedAt.toClockText()} ~ ${endedAt.toClockText()}" else "",
                    location = resortDisplayName,
                    duration = lessonDurationMinutes.toDurationText(),
                    price = totalLessonPrice,
                    teams = matchingRequests.map { it.toModel() }.toPersistentList(),
                )
            )
        }

        is InstructorLessonDetailCanceled -> LessonDetailContract.LessonDetailPhase.LessonDetailCanceled(
            cancel = LessonDetailCanceledUiModel(
                tags = listOf(sport, lessonLevel).toPersistentList(),
                classTitle = representativeConsumerNames.joinToString(),
                location = resortDisplayName,
                duration = lessonDurationMinutes.toDurationText(),
                price = totalLessonPrice,
                teams = matchingRequests.map { it.toModel() }.toPersistentList(),
            )
        )
    }

private fun MatchingRequest.toModel() = TeamParticipantsInfo(
    teamNickname = representativeMemberName,
    teamCount = headcount,
    participants = participants.map { "${it.age}세 ${it.gender.toGenderText()}" }.toPersistentList(),
    price = teamLessonPrice,
    isReady = startConfirmed,
)

private fun String.toGenderText() = when (this) {
    "MALE" -> "남"
    "FEMALE" -> "여"
    else -> this
}

private fun Int.toTimeText(): String {
    val h = this / 3600
    val m = (this % 3600) / 60
    val s = this % 60
    return "%d:%02d:%02d".format(h, m, s)
}

private fun Int.toDurationText(): String =
    when {
        this < 60 -> "${this}분"
        this % 60 == 0 -> "${this / 60}시간"
        else -> "${this / 60}시간 ${this % 60}분"
    }

private fun LocalDateTime.toClockText(): String =
    format(DateTimeFormatter.ofPattern("HH:mm"))