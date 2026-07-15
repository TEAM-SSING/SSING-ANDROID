package com.ssing.presentation.instructorlessondetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ssing.core.network.di.ApplicationScope
import com.ssing.core.network.exception.ApiException
import com.ssing.core.network.socket.SocketState
import com.ssing.core.ui.base.BaseViewModel
import com.ssing.core.ui.common.component.CancelReason
import com.ssing.core.ui.extension.uiMessage
import com.ssing.core.ui.util.ssingDateFormatter
import com.ssing.data.lesson.common.repository.api.LessonRepository
import com.ssing.data.lesson.instructor.model.InstructorConfirmedMatchingRequest
import com.ssing.data.lesson.instructor.model.InstructorLessonDetail
import com.ssing.data.lesson.instructor.model.InstructorMatchingRequest
import com.ssing.data.lesson.instructor.repository.api.InstructorLessonRepository
import com.ssing.presentation.instructorlessondetail.model.LessonDetailBeforeUiModel
import com.ssing.presentation.instructorlessondetail.model.LessonDetailCanceledUiModel
import com.ssing.presentation.instructorlessondetail.model.LessonDetailCompletedUiModel
import com.ssing.presentation.instructorlessondetail.model.LessonDetailOngoingUiModel
import com.ssing.presentation.instructorlessondetail.model.TeamParticipantsInfo
import com.ssing.presentation.instructorlessondetail.navigation.InstructorLesson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
internal class LessonDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val lessonRepository: LessonRepository,
    private val instructorLessonDetailRepository: InstructorLessonRepository,
    @param:ApplicationScope private val applicationScope: CoroutineScope,
) :
    BaseViewModel<LessonDetailContract.State, LessonDetailContract.Effect>(
        LessonDetailContract.State()
    ) {

    private val lessonId = savedStateHandle.toRoute<InstructorLesson>().lessonId

    init {
        loadLessonDetail()

        instructorLessonDetailRepository.connectSocket()
        observeSocketEvents()
        observeSocketState()
    }

    private fun observeSocketEvents() {
        instructorLessonDetailRepository.socketEvents
            .filter { it.lessonId == lessonId }
            .onEach { loadLessonDetail() }
            .launchIn(viewModelScope)
    }

    private fun observeSocketState() {
        instructorLessonDetailRepository.socketState
            .onEach { state -> handleSocketState(state) }
            .launchIn(viewModelScope)
    }

    private fun handleSocketState(state: SocketState) {
        when (state) {
            is SocketState.Error, SocketState.Forbidden ->
                sendEffect(LessonDetailContract.Effect.ShowToast("연결에 문제가 발생했어요."))

            SocketState.Connected, SocketState.Connecting, SocketState.Disconnected -> Unit
        }
    }

    private fun loadLessonDetail() {
        updateState { copy(phase = LessonDetailContract.LessonDetailPhase.Loading) }

        viewModelScope.launch {
            instructorLessonDetailRepository.fetchInstructorLessonDetail(lessonId)
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
        updateState { copy(showLessonEndDialog = true) }
    }

    fun onEndConfirmClick() {
        if (uiState.value.phase !is LessonDetailContract.LessonDetailPhase.LessonDetailOngoing) return

        updateState { copy(showLessonEndDialog = false) }
        viewModelScope.launch {
            lessonRepository.lessonCompleted(lessonId)
                .onSuccess {
                    loadLessonDetail()
                }
                .onFailure {
                    if (it is ApiException) {
                        sendEffect(LessonDetailContract.Effect.ShowToast(it.uiMessage))
                    }
                }
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
            lessonRepository.lessonStart(lessonId)
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

    private fun CancelReason.toServerCode(): String = when (this) {
        CancelReason.SCHEDULE_CHANGE -> "SCHEDULE_CHANGED"
        CancelReason.INSTRUCTOR_NO_SHOW -> "INSTRUCTOR_NOT_MET"
        CancelReason.CONSUMER_NO_SHOW -> "CONSUMER_NOT_MET"
        CancelReason.ETC -> "ETC"
    }

    fun onCancelConfirmClick(customReason: String?) {
        val reason = uiState.value.cancelReasonState.selectedReason ?: return

        viewModelScope.launch {
            lessonRepository.lessonCanceled(
                lessonId = lessonId,
                cancelReason = reason.toServerCode(),
                cancelReasonDetail = if (reason == CancelReason.ETC) customReason else null,
            ).onSuccess {
                updateState {
                    copy(
                        cancelReasonState = cancelReasonState.copy(
                            visible = false,
                            selectedReason = null,
                        )
                    )
                }
            }.onFailure {
                if (it is ApiException) {
                    sendEffect(LessonDetailContract.Effect.ShowToast(it.uiMessage))
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        applicationScope.launch { instructorLessonDetailRepository.disconnectSocket() }
    }
}

private fun InstructorLessonDetail.toPhase(): LessonDetailContract.LessonDetailPhase =
    when (this) {
        is InstructorLessonDetail.Confirmed -> LessonDetailContract.LessonDetailPhase.LessonDetailBefore(
            before = LessonDetailBeforeUiModel(
                isInstructorReady = instructorConfirmed,
                participantReadyCount = confirmedCount,
                participantTotalCount = requiredCount,
                tags = listOf(lessonInfo.basic.sport, lessonInfo.basic.lessonLevel).toPersistentList(),
                classTitle = lessonInfo.basic.representativeConsumerNames.joinToString(),
                location = lessonInfo.basic.resort.displayName,
                duration = scheduledDurationMinutes.toDurationText(),
                price = lessonInfo.totalLessonPrice,
                teams = matchingRequests.map { it.toModel() }.toPersistentList(),
            )
        )

        is InstructorLessonDetail.InProgress -> LessonDetailContract.LessonDetailPhase.LessonDetailOngoing(
            ongoing = LessonDetailOngoingUiModel(
                tags = listOf(lessonInfo.basic.sport, lessonInfo.basic.lessonLevel).toPersistentList(),
                classTitle = lessonInfo.basic.representativeConsumerNames.joinToString(),
                remainingTime = remainingSeconds.toTimeText(),
                elapsedTime = elapsedSeconds.toTimeText(),
                location = lessonInfo.basic.resort.displayName,
                duration = scheduledDurationMinutes.toDurationText(),
                price = lessonInfo.totalLessonPrice,
                teams = matchingRequests.map { it.toModel() }.toPersistentList(),
            )
        )

        is InstructorLessonDetail.Completed -> {
            val startedAt = runCatching { LocalDateTime.parse(actualStartedAt) }.getOrNull()
            val endedAt = runCatching { LocalDateTime.parse(actualEndedAt) }.getOrNull()
            LessonDetailContract.LessonDetailPhase.LessonDetailCompleted(
                completed = LessonDetailCompletedUiModel(
                    tags = listOf(lessonInfo.basic.sport, lessonInfo.basic.lessonLevel).toPersistentList(),
                    classTitle = lessonInfo.basic.representativeConsumerNames.joinToString(),
                    lessonDate = startedAt?.ssingDateFormatter() ?: "",
                    lessonTime = if (startedAt != null && endedAt != null) "${startedAt.toClockText()} ~ ${endedAt.toClockText()}" else "",
                    location = lessonInfo.basic.resort.displayName,
                    duration = lessonDurationMinutes.toDurationText(),
                    price = lessonInfo.totalLessonPrice,
                    teams = matchingRequests.map { it.toModel() }.toPersistentList(),
                )
            )
        }

        is InstructorLessonDetail.Canceled -> LessonDetailContract.LessonDetailPhase.LessonDetailCanceled(
            cancel = LessonDetailCanceledUiModel(
                tags = listOf(lessonInfo.basic.sport, lessonInfo.basic.lessonLevel).toPersistentList(),
                classTitle = lessonInfo.basic.representativeConsumerNames.joinToString(),
                location = lessonInfo.basic.resort.displayName,
                duration = lessonDurationMinutes.toDurationText(),
                price = lessonInfo.totalLessonPrice,
                teams = matchingRequests.map { it.toModel() }.toPersistentList(),
                canceledAt = runCatching { LocalDateTime.parse(canceledAt) }
                    .getOrNull()?.ssingDateFormatter() ?: canceledAt,
                canceledByName = canceledByName,
                cancelReason = cancelReason,
            )
        )
    }

private fun InstructorConfirmedMatchingRequest.toModel() = TeamParticipantsInfo(
    teamNickname = representativeMemberName,
    teamCount = headcount,
    participants = participants.map { "${it.age}세 ${it.gender.toGenderText()}" }
        .toPersistentList(),
    price = teamLessonPrice,
    isReady = startConfirmed,
)

private fun InstructorMatchingRequest.toModel() = TeamParticipantsInfo(
    teamNickname = representativeMemberName,
    teamCount = headcount,
    participants = participants.map { "${it.age}세 ${it.gender.toGenderText()}" }
        .toPersistentList(),
    price = teamLessonPrice,
    isReady = true,
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