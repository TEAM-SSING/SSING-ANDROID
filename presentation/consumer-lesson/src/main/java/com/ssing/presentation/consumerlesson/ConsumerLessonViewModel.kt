package com.ssing.presentation.consumerlesson

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ssing.core.network.di.ApplicationScope
import com.ssing.core.network.exception.ApiException
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ssing.core.network.exception.ApiException
import com.ssing.core.ui.base.BaseViewModel
import com.ssing.core.ui.common.component.CancelReason
import com.ssing.core.ui.common.component.LessonBannerState
import com.ssing.core.ui.extension.uiMessage
import com.ssing.core.ui.type.formatCountdown
import com.ssing.core.ui.type.formatDate
import com.ssing.core.ui.type.formatDateTime
import com.ssing.core.ui.type.formatMinutesText
import com.ssing.core.ui.type.formatTime
import com.ssing.data.consumerlesson.model.ConsumerLessonDetail
import com.ssing.data.consumerlesson.repository.api.ConsumerLessonDetailRepository
import com.ssing.presentation.consumerlesson.mapper.toUiModel
import com.ssing.core.ui.extension.uiMessage
import com.ssing.data.lessoncancel.repository.api.LessonCancelRepository
import com.ssing.presentation.consumerlesson.model.CanceledLessonInfoUiModel
import com.ssing.presentation.consumerlesson.model.CompletedLessonInfoUiModel
import com.ssing.presentation.consumerlesson.navigation.ConsumerLesson
import com.ssing.presentation.consumerlesson.model.InstructorProfileUiModel
import com.ssing.presentation.consumerlesson.model.LessonInfoUiModel
import com.ssing.presentation.consumerlesson.model.ParticipantTeamUiModel
import com.ssing.presentation.consumerlesson.navigation.ConsumerLesson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class ConsumerLessonViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val consumerLessonDetailRepository: ConsumerLessonDetailRepository,
    @param:ApplicationScope private val applicationScope: CoroutineScope,
) :
    BaseViewModel<ConsumerLessonContract.State, ConsumerLessonContract.Effect>(
        ConsumerLessonContract.State()
    ) {
internal class ConsumerLessonViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val lessonCancelRepository: LessonCancelRepository,
) : BaseViewModel<ConsumerLessonContract.State, ConsumerLessonContract.Effect>(
    ConsumerLessonContract.State()
) {

    val etcState = TextFieldState()

    private val lessonId: Long = savedStateHandle.toRoute<ConsumerLesson>().lessonId

    init {
        loadLessonDetail(lessonId)
        consumerLessonDetailRepository.connectSocket()
        observeSocketEvents()
    }

    private fun observeSocketEvents() {
        viewModelScope.launch {
            consumerLessonDetailRepository.socketEvents.collect { event ->
                Timber.d("강습 상세 조회 소켓 이벤트: lessonId = ${event.lessonId}, lessonStatus = ${event.lessonStatus}")
                if (event.lessonId == lessonId) {
                    loadLessonDetail(lessonId)
                }
            }
        }
    }

    fun loadLessonDetail(lessonId: Long) {
        viewModelScope.launch {
            consumerLessonDetailRepository.fetchConsumerLessonDetail(lessonId)
                .onSuccess { result ->
                    Timber.d("consumer-lesson: $result")
                    updateState { applyLessonDetail(result) }
                }
                .onFailure {
                    Timber.e(it, "consumer-lesson 실패")
                    if (it is ApiException) {
                        sendEffect(ConsumerLessonContract.Effect.ShowToast(it.uiMessage))
                    }
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        applicationScope.launch { consumerLessonDetailRepository.disconnectSocket() }
    }

    private fun ConsumerLessonContract.State.applyLessonDetail(
        detail: ConsumerLessonDetail,
    ): ConsumerLessonContract.State {
        val instructorProfileUiModel = detail.instructorProfile.toUiModel()

        return when (detail) {
            is ConsumerLessonDetail.Confirmed -> copy(
                lessonBannerState = LessonBannerState.Before(
                    isInstructorReady = detail.instructorConfirmed,
                    participantReadyCount = detail.confirmedCount,
                    participantTotalCount = detail.requiredCount,
                ),
                lessonInfo = detail.lessonInfo.toUiModel(
                    durationMinutes = detail.scheduledDurationMinutes,
                    matchingRequests = detail.lessonMatchingRequest,
                ),
                instructorProfile = instructorProfileUiModel,
                participantTeams = detail.lessonMatchingRequest
                    .map { it.toUiModel() }
                    .toPersistentList(),
                isReady = detail.currentActorConfirmed,
            )

            is ConsumerLessonDetail.InProgress -> copy(
                lessonBannerState = LessonBannerState.Ongoing(
                    remainingTime = formatCountdown(detail.remainingSeconds),
                    elapsedTime = formatMinutesText(detail.elapsedSeconds / 60),
                ),
                lessonInfo = detail.lessonInfo.toUiModel(
                    durationMinutes = detail.scheduledDurationMinutes,
                    matchingRequests = detail.lessonMatchingRequest,
                ),
                instructorProfile = instructorProfileUiModel,
                participantTeams = detail.lessonMatchingRequest
                    .map { it.toUiModel() }
                    .toPersistentList(),
            )

            is ConsumerLessonDetail.Completed -> copy(
                lessonBannerState = LessonBannerState.Completed(
                    lessonDate = formatDate(detail.actualEndedAt)
                ),
                instructorProfile = instructorProfileUiModel,
                lessonInfo = null,
                participantTeams = persistentListOf(),
                completedLessonInfo = CompletedLessonInfoUiModel(
                    lessonInfo = detail.lessonInfo.toUiModel(
                        durationMinutes = detail.lessonDurationMinutes,
                        matchingRequests = emptyList(),
                    ),
                    actualTimeRange = "${formatTime(detail.actualStartedAt)}~" +
                            "${formatTime(detail.actualEndedAt)} " +
                            "(${formatMinutesText(detail.actualDurationMinutes)})",
                ),
            )

            is ConsumerLessonDetail.Canceled -> copy(
                lessonBannerState = LessonBannerState.Canceled,
                instructorProfile = instructorProfileUiModel,
                participantTeams = persistentListOf(),
                canceledLessonInfo = CanceledLessonInfoUiModel(
                    lessonInfo = detail.lessonInfo.toUiModel(
                        durationMinutes = detail.lessonDurationMinutes,
                        matchingRequests = emptyList(),
                    ),
                    cancelDateTime = formatDateTime(detail.canceledAt),
                    cancelSubject = detail.canceledByName,
                    cancelReason = detail.cancelReason,
                ),
            )
        }
    }

    fun onBack() = sendEffect(ConsumerLessonContract.Effect.NavigationToHome)

    fun onReadyClick() {
        updateState { copy(showReadyAlert = true) }
    }

    fun onReadyDismissed() {
        updateState { copy(showReadyAlert = false) }
    }

    // TODO: 서버 연동 후 수정 - [테스트] participantReadyCount를 로컬에서 직접 증가시킴
    fun onReadyConfirmed() {
        var isAllReady = false

        updateState {
            val before = lessonBannerState as? LessonBannerState.Before ?: return@updateState this
            val updatedBanner =
                before.copy(participantReadyCount = before.participantReadyCount + 1)
            isAllReady = updatedBanner.totalReadyCount == updatedBanner.totalCount

            copy(
                isReady = true,
                showReadyAlert = false,
                lessonBannerState = updatedBanner,
            )
        }

        if (isAllReady) {
            onLessonStarted(
                remainingTime = "2:59:59",
                elapsedTime = "0분",
            )
        }
    }

    fun onEndLessonClick() {
        updateState { copy(showEndLessonAlert = true) }
    }

    fun onEndLessonDismiss() {
        updateState { copy(showEndLessonAlert = false) }
    }

    fun onEndLessonConfirmed() {
        updateState {
            copy(
                showEndLessonAlert = false,
                lessonBannerState = LessonBannerState.Completed(
                    lessonDate = "2026년 12월 31일",
                ),
            )
        }
    }

    fun onReviewClick() = sendEffect(ConsumerLessonContract.Effect.ShowToast("준비 중인 기능입니다."))

    fun onCancelClick() {
        updateState { copy(showCancelConfirmSheet = true) }
    }

    fun onReasonSelected(reason: CancelReason) {
        updateState { copy(selectedReason = reason) }
    }

    private fun CancelReason.toServerCode(): String = when (this) {
        CancelReason.SCHEDULE_CHANGE -> "SCHEDULE_CHANGED"
        CancelReason.INSTRUCTOR_NO_SHOW -> "INSTRUCTOR_NOT_MET"
        CancelReason.CONSUMER_NO_SHOW -> "CONSUMER_NOT_MET"
        CancelReason.ETC -> "ETC"
    }

    fun onCancelConfirmed() {
        val reason = uiState.value.selectedReason ?: return
        val etcReason = if (uiState.value.selectedReason == CancelReason.ETC) {
            etcState.text.toString()
        } else {
            null
        }

        viewModelScope.launch {
            lessonCancelRepository.postLessonCancel(
                lessonId = lessonId,
                cancelReason = reason.toServerCode(),
                cancelReasonDetail = etcReason,
            ).onSuccess {
                updateState {
                    copy(
                        lessonBannerState = LessonBannerState.Canceled,
                        showCancelConfirmSheet = false,
                        selectedReason = null,
                    )
                }
                etcState.edit { replace(0, length, "") }
            }.onFailure {
                Timber.e(it, "강습 취소 실패")
                if (it is ApiException) {
                    sendEffect(ConsumerLessonContract.Effect.ShowToast(it.uiMessage))
                }
            }
        }
    }

    fun onCancelDismiss() {
        updateState {
            copy(
                showCancelConfirmSheet = false,
                selectedReason = null,
            )
        }
        etcState.edit { replace(0, length, "") }
    }

    fun onChatClick() = sendEffect(ConsumerLessonContract.Effect.ShowToast("준비 중인 기능입니다."))

    fun onLessonStarted(remainingTime: String, elapsedTime: String) {
        updateState {
            copy(
                lessonBannerState = LessonBannerState.Ongoing(
                    remainingTime = remainingTime,
                    elapsedTime = elapsedTime,
                ),
                participantTeams = participantTeams
                    .map { it.copy(isReady = false) }
                    .toPersistentList(),
            )
        }
    }

    fun onReportIssueClick() = sendEffect(ConsumerLessonContract.Effect.ShowToast("준비 중인 기능입니다."))

    fun onAdditionalLessonClick() =
        sendEffect(ConsumerLessonContract.Effect.ShowToast("준비 중인 기능입니다."))

    fun onLessonListClick() = sendEffect(ConsumerLessonContract.Effect.ShowToast("준비 중인 기능입니다."))

    fun onHomeClick() = sendEffect(ConsumerLessonContract.Effect.NavigationToHome)
}