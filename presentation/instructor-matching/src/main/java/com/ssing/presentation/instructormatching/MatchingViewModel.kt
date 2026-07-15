package com.ssing.presentation.instructormatching

import androidx.lifecycle.viewModelScope
import com.ssing.core.network.di.ApplicationScope
import com.ssing.core.network.exception.ApiException
import com.ssing.core.network.socket.SocketState
import com.ssing.core.ui.base.BaseViewModel
import com.ssing.core.ui.extension.uiMessage
import com.ssing.data.matching.instructormatching.event.InstructorMatchingEvent
import com.ssing.data.matching.instructormatching.model.InstructorMatchingOffer
import com.ssing.data.matching.instructormatching.repository.api.InstructorMatchingRepository
import com.ssing.presentation.instructormatching.MatchingContract.MatchingDialog
import com.ssing.presentation.instructormatching.MatchingContract.MatchingPhase
import com.ssing.presentation.instructormatching.model.DurationOption
import com.ssing.presentation.instructormatching.model.LessonSummaryUiModel
import com.ssing.presentation.instructormatching.model.LevelOption
import com.ssing.presentation.instructormatching.model.MatchingOfferUiModel
import com.ssing.presentation.instructormatching.model.OfferStatusOption
import com.ssing.presentation.instructormatching.model.SportOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class MatchingViewModel @Inject constructor(
    private val instructorMatchingRepository: InstructorMatchingRepository,
    @param:ApplicationScope private val applicationScope: CoroutineScope,
) :
    BaseViewModel<MatchingContract.State, MatchingContract.Effect>(
        MatchingContract.State()
    ) {

    init {
        loadMatchingExposure()
        instructorMatchingRepository.connectSocket()

        instructorMatchingRepository.event
            .onEach { handleMatchingEvent(it) }
            .launchIn(viewModelScope)

        instructorMatchingRepository.socketState
            .onEach { handleSocketState(it) }
            .launchIn(viewModelScope)
    }

    private fun handleMatchingEvent(event: InstructorMatchingEvent) {
        Timber.d("matching 소켓 이벤트: ${event::class.simpleName}")
        when (event) {
            is InstructorMatchingEvent.OfferReceivedEvent,
            is InstructorMatchingEvent.OfferClosedEvent,
            -> restoreActiveOffer()

            is InstructorMatchingEvent.MatchingCanceledEvent -> {
                updateState { copy(phase = MatchingPhase.Waiting) }
                sendEffect(MatchingContract.Effect.ShowToast(MSG_CONSUMER_REJECTED))
            }

            is InstructorMatchingEvent.MatchingConfirmedEvent -> onMatchingConfirmed(event.lessonId)
        }
    }

    private var socketErrorToastShown = false

    private fun handleSocketState(state: SocketState) {
        when (state) {
            is SocketState.Error, SocketState.Forbidden -> {
                if (!socketErrorToastShown) {
                    socketErrorToastShown = true
                    sendEffect(MatchingContract.Effect.ShowToast("연결에 문제가 발생했어요."))
                }
            }
            SocketState.Connected -> socketErrorToastShown = false
            SocketState.Connecting, SocketState.Disconnected -> Unit
        }
    }

    private fun onMatchingConfirmed(lessonId: Long) = viewModelScope.launch {
        Timber.d("매칭 확정 수신 → 강습 상세 이동 (lessonId=$lessonId)")
        try {
            instructorMatchingRepository.disconnectSocket()
        } finally {
            sendEffect(MatchingContract.Effect.NavigateToLessonDetail(lessonId))
        }
    }

    private fun loadMatchingExposure() {
        viewModelScope.launch {
            instructorMatchingRepository.fetchMatchingExposure()
                .onSuccess { result ->
                    Timber.d("matching-exposure 응답: $result")
                    updateState {
                        copy(
                            exposure = exposure.applyProfile(
                                availableSports = result.availableSports.toSportOptions(),
                                resortName = result.resort.displayName,
                            ),
                        )
                    }
                }
                .onFailure {
                    Timber.e(it, "matching-exposure 실패")
                    if (it is ApiException) {
                        sendEffect(MatchingContract.Effect.ShowToast(it.uiMessage))
                    }
                }
        }
    }

    private fun List<String>.toSportOptions(): Set<SportOption> =
        mapNotNull { code -> runCatching { SportOption.valueOf(code) }.getOrNull() }.toSet()

    fun selectSport(sport: SportOption) = updateState {
        copy(exposure = exposure.copy(selectedSports = sport))
    }

    fun toggleLevel(level: LevelOption) = updateState {
        copy(exposure = exposure.copy(selectedLevels = exposure.selectedLevels.toggle(level)))
    }

    fun toggleDuration(duration: DurationOption) = updateState {
        copy(exposure = exposure.copy(selectedDurations = exposure.selectedDurations.toggle(duration)))
    }

    fun changeMaxHeadcount(count: Int) = updateState {
        copy(exposure = exposure.copy(maxHeadcount = count))
    }

    fun changeNoticeChecked(checked: Boolean) = updateState {
        copy(exposure = exposure.copy(isNoticeChecked = checked))
    }

    fun onBack() = sendEffect(MatchingContract.Effect.NavigateBack)

    fun startMatching() {
        val current = uiState.value.exposure
        val sport = current.selectedSports ?: return
        if (!current.isStartEnabled) return

        updateState { copy(exposure = exposure.copy(isSubmitting = true)) }
        viewModelScope.launch {
            instructorMatchingRepository.startMatchingExposure(
                sport = sport.name,
                lessonLevels = current.selectedLevels.map { it.name },
                availableDurationMinutes = current.selectedDurations.map { it.hours * 60 },
                maxHeadcount = current.maxHeadcount,
                equipmentReady = current.isNoticeChecked,
            )
                .onSuccess { isExposed ->
                    Timber.d("matching-exposure 저장 응답 isExposed=$isExposed")
                    updateState {
                        copy(
                            phase = MatchingPhase.Waiting,
                            exposure = exposure.copy(isSubmitting = false),
                        )
                    }
                }
                .onFailure {
                    Timber.e(it, "matching-exposure 저장 실패")
                    if (it is ApiException) {
                        sendEffect(MatchingContract.Effect.ShowToast(it.uiMessage))
                    }
                    updateState { copy(exposure = exposure.copy(isSubmitting = false)) }
                }
        }
    }

    fun editExposure() = updateState {
        copy(phase = MatchingPhase.SettingExposure)
    }

    fun stopWaiting() = updateState {
        copy(dialog = MatchingDialog.StopWaiting)
    }

    fun confirmStopWaiting() {
        viewModelScope.launch {
            instructorMatchingRepository.cancelMatchingExposure()
                .onSuccess { isExposed ->
                    Timber.d("즉시노출 중단 응답 isExposed=$isExposed")
                    updateState { copy(dialog = null, phase = MatchingPhase.SettingExposure) }
                }
                .onFailure {
                    Timber.e(it, "즉시노출 중단 실패")
                    if (it is ApiException) {
                        sendEffect(MatchingContract.Effect.ShowToast(it.uiMessage))
                    }
                    updateState { copy(dialog = null) }
                }
        }
    }

    fun acceptOffer() {
        val offer = currentOffer() ?: return
        viewModelScope.launch {
            instructorMatchingRepository.respondMatchingOffer(offer.offerId, DECISION_ACCEPTED)
                .onSuccess { result ->
                    Timber.d("매칭 제안 수락 응답: $result")
                    updateState {
                        copy(
                            phase = MatchingPhase.PendingConfirm(
                                offer = offer,
                                confirmationExpiresAtMillis = null,
                            )
                        )
                    }
                }
                .onFailure {
                    Timber.e(it, "매칭 제안 수락 실패")
                    if (it is ApiException) {
                        sendEffect(MatchingContract.Effect.ShowToast(it.uiMessage))
                    }
                }
        }
    }

    fun rejectOffer() {
        val offer = currentOffer() ?: return
        viewModelScope.launch {
            instructorMatchingRepository.respondMatchingOffer(offer.offerId, DECISION_REJECTED)
                .onSuccess { result ->
                    Timber.d("매칭 제안 거절 응답: $result")
                    updateState { copy(phase = MatchingPhase.Waiting) }
                }
                .onFailure {
                    Timber.e(it, "매칭 제안 거절 실패")
                    if (it is ApiException) {
                        sendEffect(MatchingContract.Effect.ShowToast(it.uiMessage))
                    }
                }
        }
    }

    fun dismissDialog() = updateState { copy(dialog = null) }

    fun restoreActiveOffer() {
        viewModelScope.launch {
            instructorMatchingRepository.fetchActiveOffer()
                .onSuccess { offer ->
                    Timber.d("matching-offers 응답: $offer")
                    updateState {
                        when {
                            offer != null -> copy(phase = MatchingPhase.OfferArrived(offer.toUiModel()))
                            phase is MatchingPhase.OfferArrived || phase is MatchingPhase.PendingConfirm -> copy(
                                phase = MatchingPhase.Waiting
                            )

                            else -> this
                        }
                    }
                }
                .onFailure {
                    Timber.e(it, "matching-offers 실패")
                    if (it is ApiException) {
                        sendEffect(MatchingContract.Effect.ShowToast(it.uiMessage))
                    }
                }
        }
    }

    private fun currentOffer(): MatchingOfferUiModel? =
        when (val phase = uiState.value.phase) {
            is MatchingPhase.OfferArrived -> phase.offer
            is MatchingPhase.PendingConfirm -> phase.offer
            else -> null
        }

    private fun InstructorMatchingOffer.toUiModel(): MatchingOfferUiModel = MatchingOfferUiModel(
        offerId = offerId,
        groupId = groupId,
        status = runCatching { OfferStatusOption.valueOf(offerStatus) }
            .getOrDefault(OfferStatusOption.UNKNOWN),
        expiresAtMillis = expiresAt?.let {
            runCatching {
                java.time.Instant.parse(it).toEpochMilli()
            }.getOrNull()
        },
        nickname = requestSummary.requesterName,
        teamCount = requestSummary.headcount,
        price = priceSummary.totalPaymentAmount,
        // TODO(#151, 서버 대기): participants(수강생 나이/성별) — offer 응답에 추가 예정. 내려오면 여기서 채운다.
        participants = emptyList(),
        lesson = LessonSummaryUiModel(
            resortLabel = lessonSummary.resort.displayName,
            sportLabel = lessonSummary.sport.toSportLabel(),
            levelLabel = lessonSummary.level.toLevelLabel(),
            headcount = lessonSummary.totalHeadcount,
            durationHours = DurationOption.entries
                .firstOrNull { it.hours * 60 == lessonSummary.durationMinutes }?.hours
                ?: (lessonSummary.durationMinutes / 60),
        ),
    )

    private fun String.toSportLabel(): String =
        runCatching { SportOption.valueOf(this).label }.getOrDefault(this)

    private fun String.toLevelLabel(): String =
        runCatching { LevelOption.valueOf(this).label }.getOrDefault(this)

    private fun <T> Set<T>.toggle(item: T): Set<T> =
        if (item in this) this - item else this + item

    override fun onCleared() {
        super.onCleared()
        applicationScope.launch { instructorMatchingRepository.disconnectSocket() }
    }

    private companion object {
        const val DECISION_ACCEPTED = "ACCEPTED"
        const val DECISION_REJECTED = "REJECTED"

        const val MSG_CONSUMER_REJECTED = "강습생이 매칭을 거절했어요.\n같은 조건으로 바로 다른 강습생을 찾고있어요."
    }
}
