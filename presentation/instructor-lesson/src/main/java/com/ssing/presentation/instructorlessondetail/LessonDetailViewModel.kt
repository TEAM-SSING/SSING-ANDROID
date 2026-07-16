package com.ssing.presentation.instructorlessondetail

import android.os.SystemClock
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ssing.core.network.di.ApplicationScope
import com.ssing.core.network.exception.ApiException
import com.ssing.core.network.socket.SocketState
import com.ssing.core.ui.base.BaseViewModel
import com.ssing.core.ui.common.component.CancelReason
import com.ssing.core.ui.extension.uiMessage
import com.ssing.core.ui.type.displayGender
import com.ssing.core.ui.type.displayLessonLevel
import com.ssing.core.ui.type.displaySport
import com.ssing.core.ui.type.formatCountdown
import com.ssing.core.ui.type.formatDate
import com.ssing.core.ui.type.formatMinutesText
import com.ssing.core.ui.type.formatTime
import com.ssing.core.ui.util.ssingDateFormatter
import com.ssing.data.lesson.common.model.LessonStartConfirmationResult
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
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.time.LocalDateTime
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

    private var tickerJob: Job? = null

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

                    if (result is InstructorLessonDetail.InProgress) {
                        startTicking(
                            remainingSeconds = result.remainingSeconds,
                            elapsedSeconds = result.elapsedSeconds,
                        )
                    } else {
                        stopTicking()
                    }
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
            (uiState.value.phase as? LessonDetailContract.LessonDetailPhase.LessonDetailBefore)?.before

        if (before == null) {
            loadLessonDetail()
            return
        }

        updateState { copy(showReadyDialog = false) }

        viewModelScope.launch {
            lessonRepository.lessonStart(lessonId)
                .onSuccess { result ->
                    when (result) {
                        is LessonStartConfirmationResult.Pending -> updateState {
                            copy(
                                phase = LessonDetailContract.LessonDetailPhase.LessonDetailBefore(
                                    before = before.copy(
                                        isInstructorReady = result.instructorConfirmed,
                                        participantReadyCount = result.confirmedCount,
                                        participantTotalCount = result.requiredCount,
                                    )
                                )
                            )
                        }

                        is LessonStartConfirmationResult.Started -> loadLessonDetail()
                    }
                }
                .onFailure {
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
                loadLessonDetail()
            }.onFailure {
                if (it is ApiException) {
                    sendEffect(LessonDetailContract.Effect.ShowToast(it.uiMessage))
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopTicking()
        applicationScope.launch { instructorLessonDetailRepository.disconnectSocket() }
    }

    private fun startTicking(remainingSeconds: Int, elapsedSeconds: Int) {
        tickerJob?.cancel()
        val syncedAt = SystemClock.elapsedRealtime()

        tickerJob = viewModelScope.launch {
            while (isActive) {
                val secondsPassed = ((SystemClock.elapsedRealtime() - syncedAt) / 1000).toInt()
                val currentRemaining = (remainingSeconds - secondsPassed).coerceAtLeast(0)
                val currentElapsed = elapsedSeconds + secondsPassed

                updateState {
                    val currentPhase = phase as? LessonDetailContract.LessonDetailPhase.LessonDetailOngoing
                    if (currentPhase != null) {
                        copy(
                            phase = currentPhase.copy(
                                ongoing = currentPhase.ongoing.copy(
                                    remainingTime = formatCountdown(currentRemaining),
                                    elapsedTime = formatMinutesText(currentElapsed/60),
                                )
                            )
                        )
                    } else {
                        this
                    }
                }

                if (currentRemaining <= 0) {
                    break
                }
                delay(1000)
            }
        }
    }

    private fun stopTicking() {
        tickerJob?.cancel()
        tickerJob = null
    }
}

private fun InstructorLessonDetail.toPhase(): LessonDetailContract.LessonDetailPhase =
    when (this) {
        is InstructorLessonDetail.Confirmed -> LessonDetailContract.LessonDetailPhase.LessonDetailBefore(
            before = LessonDetailBeforeUiModel(
                isInstructorReady = instructorConfirmed,
                participantReadyCount = confirmedCount,
                participantTotalCount = requiredCount,
                tags = persistentListOf(displaySport(lessonInfo.basic.sport), displayLessonLevel(lessonInfo.basic.lessonLevel)),
                classTitle = lessonInfo.basic.representativeConsumerNames.joinToString(),
                location = lessonInfo.basic.resort.displayName,
                duration = formatMinutesText(scheduledDurationMinutes),
                price = lessonInfo.totalLessonPrice,
                teams = matchingRequests.map { it.toModel() }.toPersistentList(),
            )
        )

        is InstructorLessonDetail.InProgress -> LessonDetailContract.LessonDetailPhase.LessonDetailOngoing(
            ongoing = LessonDetailOngoingUiModel(
                tags = persistentListOf(displaySport(lessonInfo.basic.sport), displayLessonLevel(lessonInfo.basic.lessonLevel)),
                classTitle = lessonInfo.basic.representativeConsumerNames.joinToString(),
                remainingTime = formatCountdown(remainingSeconds),
                elapsedTime = formatMinutesText(elapsedSeconds / 60),
                location = lessonInfo.basic.resort.displayName,
                duration = formatMinutesText(scheduledDurationMinutes),
                price = lessonInfo.totalLessonPrice,
                teams = matchingRequests.map { it.toModel() }.toPersistentList(),
            )
        )

        is InstructorLessonDetail.Completed -> {
            LessonDetailContract.LessonDetailPhase.LessonDetailCompleted(
                completed = LessonDetailCompletedUiModel(
                    tags = persistentListOf(displaySport(lessonInfo.basic.sport), displayLessonLevel(lessonInfo.basic.lessonLevel)),
                    classTitle = lessonInfo.basic.representativeConsumerNames.joinToString(),
                    lessonDate = formatDate(actualStartedAt),
                    lessonTime = "${formatTime(actualStartedAt)} ~ ${formatTime(actualEndedAt)} (${formatMinutesText(actualDurationMinutes)})",
                    location = lessonInfo.basic.resort.displayName,
                    duration = formatMinutesText(lessonDurationMinutes),
                    price = lessonInfo.totalLessonPrice,
                    teams = matchingRequests.map { it.toModel() }.toPersistentList(),
                )
            )
        }

        is InstructorLessonDetail.Canceled -> LessonDetailContract.LessonDetailPhase.LessonDetailCanceled(
            cancel = LessonDetailCanceledUiModel(
                tags = persistentListOf(displaySport(lessonInfo.basic.sport), displayLessonLevel(lessonInfo.basic.lessonLevel)),
                classTitle = lessonInfo.basic.representativeConsumerNames.joinToString(),
                location = lessonInfo.basic.resort.displayName,
                duration = formatMinutesText(lessonDurationMinutes),
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
    participants = participants.map { "${it.age}세 ${displayGender(it.gender)}" }
        .toPersistentList(),
    price = teamLessonPrice,
    isReady = startConfirmed,
)

private fun InstructorMatchingRequest.toModel() = TeamParticipantsInfo(
    teamNickname = representativeMemberName,
    teamCount = headcount,
    participants = participants.map { "${it.age}세 ${displayGender(it.gender)}" }
        .toPersistentList(),
    price = teamLessonPrice,
    isReady = true,
)