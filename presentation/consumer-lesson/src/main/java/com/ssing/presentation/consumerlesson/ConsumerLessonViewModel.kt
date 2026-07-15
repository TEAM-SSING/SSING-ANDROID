package com.ssing.presentation.consumerlesson

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ssing.core.network.di.ApplicationScope
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
import com.ssing.data.lesson.common.repository.api.LessonRepository
import com.ssing.data.lesson.consumer.model.ConsumerLessonDetail
import com.ssing.data.lesson.consumer.repository.api.ConsumerLessonRepository
import com.ssing.presentation.consumerlesson.mapper.toUiModel
import com.ssing.presentation.consumerlesson.model.CanceledLessonInfoUiModel
import com.ssing.presentation.consumerlesson.model.CompletedLessonInfoUiModel
import com.ssing.presentation.consumerlesson.navigation.ConsumerLesson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class ConsumerLessonViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val consumerLessonRepository: ConsumerLessonRepository,
    private val lessonRepository: LessonRepository,
    @param:ApplicationScope private val applicationScope: CoroutineScope,
) :
    BaseViewModel<ConsumerLessonContract.State, ConsumerLessonContract.Effect>(
        ConsumerLessonContract.State()
    ) {

    val etcState = TextFieldState()

    private val lessonId: Long = savedStateHandle.toRoute<ConsumerLesson>().lessonId

    init {
        loadLessonDetail(lessonId)
        consumerLessonRepository.connectSocket()
        observeSocketEvents()
    }

    private fun observeSocketEvents() {
        viewModelScope.launch {
            consumerLessonRepository.socketEvents.collect { event ->
                Timber.d("강습 상세 조회 소켓 이벤트: lessonId = ${event.lessonId}, lessonStatus = ${event.lessonStatus}")
                if (event.lessonId == lessonId) {
                    loadLessonDetail(lessonId)
                }
            }
        }
    }

    fun loadLessonDetail(lessonId: Long) {
        viewModelScope.launch {
            consumerLessonRepository.fetchConsumerLessonDetail(lessonId)
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
        applicationScope.launch { consumerLessonRepository.disconnectSocket() }
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
                    matchingRequests = detail.matchingRequests,
                ),
                instructorProfile = instructorProfileUiModel,
                participantTeams = detail.matchingRequests
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
                    matchingRequests = detail.matchingRequests,
                ),
                instructorProfile = instructorProfileUiModel,
                participantTeams = detail.matchingRequests
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

    fun onReadyConfirmed() {
        val before = uiState.value.lessonBannerState as? LessonBannerState.Before ?: return
        val updatedBanner = before.copy(participantReadyCount = before.participantReadyCount + 1)

        updateState {
            copy(
                isReady = true,
                showReadyAlert = false,
                lessonBannerState = updatedBanner,
            )
        }

        viewModelScope.launch {
            lessonRepository.lessonStart(lessonId)
                .onFailure {
                    updateState {
                        copy(isReady = false, lessonBannerState = before)
                    }
                    if (it is ApiException) {
                        sendEffect(ConsumerLessonContract.Effect.ShowToast(it.uiMessage))
                    }
                }
        }
    }

    fun onEndLessonClick() {
        updateState { copy(showEndLessonAlert = true) }
    }

    fun onEndLessonDismiss() {
        updateState { copy(showEndLessonAlert = false) }
    }

    fun onEndLessonConfirmed() {
        updateState { copy(showEndLessonAlert = false) }

        viewModelScope.launch {
            lessonRepository.lessonCompleted(lessonId)
                .onFailure {
                    if (it is ApiException) {
                        sendEffect(ConsumerLessonContract.Effect.ShowToast(it.uiMessage))
                    }
                }
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
            lessonRepository.lessonCanceled(
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

    fun onReportIssueClick() = sendEffect(ConsumerLessonContract.Effect.ShowToast("준비 중인 기능입니다."))

    fun onAdditionalLessonClick() =
        sendEffect(ConsumerLessonContract.Effect.ShowToast("준비 중인 기능입니다."))

    fun onLessonListClick() = sendEffect(ConsumerLessonContract.Effect.ShowToast("준비 중인 기능입니다."))

    fun onHomeClick() = sendEffect(ConsumerLessonContract.Effect.NavigationToHome)
}