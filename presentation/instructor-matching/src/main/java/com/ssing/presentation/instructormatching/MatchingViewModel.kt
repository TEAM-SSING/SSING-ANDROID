package com.ssing.presentation.instructormatching

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ssing.core.network.di.ApplicationScope
import com.ssing.core.network.exception.ApiException
import com.ssing.core.network.socket.SocketState
import com.ssing.core.ui.base.BaseViewModel
import com.ssing.core.ui.extension.uiMessage
import com.ssing.data.matching.instructormatching.event.InstructorMatchingEvent
import com.ssing.data.matching.instructormatching.model.InstructorMatchingOfferDetail
import com.ssing.data.matching.instructormatching.model.InstructorMatchingSetting
import com.ssing.data.home.repository.api.HomeRepository
import com.ssing.data.matching.instructormatching.repository.api.InstructorMatchingRepository
import com.ssing.presentation.instructormatching.MatchingContract.MatchingDialog
import com.ssing.presentation.instructormatching.MatchingContract.MatchingPhase
import com.ssing.presentation.instructormatching.navigation.InstructorMatching
import com.ssing.presentation.instructormatching.model.DurationOption
import com.ssing.presentation.instructormatching.model.LessonSummaryUiModel
import com.ssing.presentation.instructormatching.model.LevelOption
import com.ssing.presentation.instructormatching.model.MatchingExposureUiState
import com.ssing.presentation.instructormatching.model.MatchingOfferUiModel
import com.ssing.presentation.instructormatching.model.OfferStatusOption
import com.ssing.presentation.instructormatching.model.ParticipantUiModel
import com.ssing.presentation.instructormatching.model.SportOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class MatchingViewModel @Inject constructor(
    private val instructorMatchingRepository: InstructorMatchingRepository,
    private val homeRepository: HomeRepository,
    @param:ApplicationScope private val applicationScope: CoroutineScope,
    savedStateHandle: SavedStateHandle,
) :
    BaseViewModel<MatchingContract.State, MatchingContract.Effect>(
        MatchingContract.State()
    ) {

    init {
        loadMatchingExposure()
        // FCM '새 강습 도착' 딥링크로 진입하면 offerId가 담겨온다.
        // offerId가 있으면 그 제안 상세를, 없으면(거절/일반 진입) 활성 제안을 조회한다.
        val offerId = savedStateHandle.toRoute<InstructorMatching>().offerId
        if (offerId != null) {
            restoreOfferDetail(offerId)
        } else {
            restoreActiveOffer()
        }
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
            is InstructorMatchingEvent.OfferReceivedEvent -> {
                if (uiState.value.phase is MatchingPhase.PendingConfirm) {
                    sendEffect(MatchingContract.Effect.ShowToast(MSG_CONSUMER_REJECTED))
                    restoreActiveOffer()
                } else {
                    restoreOfferDetail(event.offerId)
                }
            }
            is InstructorMatchingEvent.OfferClosedEvent -> restoreOfferDetail(event.offerId)

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

    private var restoreJob: Job? = null

    fun restoreActiveOffer() {
        restoreJob?.cancel()
        restoreJob = viewModelScope.launch {
            instructorMatchingRepository.fetchMatchingActive()
                .onSuccess { active ->
                    Timber.d("matching-offers 응답: $active")
                    // 저장된 조건은 항상 복원한다(대기 화면 조건 카드/조건 수정용).
                    updateState { copy(exposure = exposure.applyMatchingSetting(active.setting)) }
                    val offerId = active.offerId
                    when {
                        offerId != null -> restoreOfferDetail(offerId)
                        active.setting.isExposed -> updateState { copy(phase = MatchingPhase.Waiting) }
                        else -> updateState { copy(phase = MatchingPhase.SettingExposure) }
                    }
                }
                .onFailure {
                    Timber.e(it, "matching-offers 실패")
                    if (it is ApiException.Conflict) {
                        updateState { copy(phase = MatchingPhase.SettingExposure) }
                    } else if (it is ApiException) {
                        sendEffect(MatchingContract.Effect.ShowToast(it.uiMessage))
                    }
                }
        }
    }

    fun restoreOfferDetail(offerId: Long) {
        restoreJob?.cancel()
        restoreJob = viewModelScope.launch {
            instructorMatchingRepository.fetchOfferDetail(offerId)
                .onSuccess { detail ->
                    Timber.d("matching-offer 상세 응답: $detail")
                    when (detail) {
                        is InstructorMatchingOfferDetail.Available ->
                            updateState { copy(phase = detail.toPhase()) }

                        is InstructorMatchingOfferDetail.Stale ->
                            navigateToLessonIfConfirmed(detail.offerId)
                    }
                }
                .onFailure {
                    Timber.e(it, "matching-offer 상세 실패")
                    if (it is ApiException.Conflict) {
                        updateState { copy(phase = MatchingPhase.SettingExposure) }
                    } else if (it is ApiException) {
                        sendEffect(MatchingContract.Effect.ShowToast(it.uiMessage))
                    }
                }
        }
    }

    private suspend fun navigateToLessonIfConfirmed(offerId: Long) {
        homeRepository.getInstructorHome()
            .onSuccess { summary ->
                val lessonId = summary.lessonCards
                    .firstOrNull { card ->
                        card.offerId == offerId &&
                            card.lessonId != null &&
                            card.displayStatus in NAVIGABLE_LESSON_STATUSES
                    }?.lessonId

                if (lessonId != null) {
                    Timber.d("Stale 오퍼 → 강습 상세 이동 (offerId=$offerId, lessonId=$lessonId)")
                    sendEffect(MatchingContract.Effect.NavigateToLessonDetail(lessonId))
                } else {
                    Timber.d("Stale 오퍼 → 매칭 대기로 전환 (offerId=$offerId)")
                    updateState {
                        when (phase) {
                            is MatchingPhase.OfferArrived,
                            is MatchingPhase.PendingConfirm,
                                -> copy(phase = MatchingPhase.Waiting)
                            else -> this
                        }
                    }
                }
            }
            .onFailure {
                Timber.e(it, "홈 재조회 실패 → 매칭 대기로 전환")
                updateState {
                    when (phase) {
                        is MatchingPhase.OfferArrived,
                        is MatchingPhase.PendingConfirm,
                            -> copy(phase = MatchingPhase.Waiting)
                        else -> this
                    }
                }
            }
    }

    private fun InstructorMatchingOfferDetail.Available.toPhase(): MatchingPhase =
        when (matchingStatus) {
            MATCHING_STATUS_WAITING_FOR_INSTRUCTOR -> MatchingPhase.OfferArrived(toUiModel())
            MATCHING_STATUS_WAITING_FOR_CONFIRMATION,
            MATCHING_STATUS_PAYMENT_PENDING,
                -> MatchingPhase.PendingConfirm(
                offer = toUiModel(),
                confirmationExpiresAtMillis = null
            )

            else -> MatchingPhase.OfferArrived(toUiModel())
        }

    private fun InstructorMatchingOfferDetail.Available.toUiModel(): MatchingOfferUiModel =
        MatchingOfferUiModel(
            offerId = offerId,
            groupId = groupId,
            status = runCatching { OfferStatusOption.valueOf(offerStatus) }
                .getOrDefault(OfferStatusOption.UNKNOWN),
            // 무기한 대기 정책 — 상세 응답에 expiresAt/타이머가 없다.
            expiresAtMillis = null,
            nickname = requestSummary.requesterName,
            teamCount = requestSummary.headcount,
            price = priceSummary.totalPaymentAmount,
            participants = participants.map {
                ParticipantUiModel(age = it.age, isMale = it.gender == GENDER_MALE)
            },
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

    private fun MatchingExposureUiState.applyMatchingSetting(
        setting: InstructorMatchingSetting,
    ): MatchingExposureUiState = copy(
        resortName = setting.resort.displayName,
        selectedSports = runCatching { SportOption.valueOf(setting.sport) }.getOrNull(),
        selectedLevels = setting.lessonLevels
            .mapNotNull { runCatching { LevelOption.valueOf(it) }.getOrNull() }
            .toSet(),
        selectedDurations = setting.availableDurationMinutes
            .mapNotNull { minutes -> DurationOption.entries.firstOrNull { it.hours * 60 == minutes } }
            .toSet(),
        maxHeadcount = setting.maxHeadcount,
        isNoticeChecked = setting.equipmentReady,
    )

    private fun currentOffer(): MatchingOfferUiModel? =
        when (val phase = uiState.value.phase) {
            is MatchingPhase.OfferArrived -> phase.offer
            is MatchingPhase.PendingConfirm -> phase.offer
            else -> null
        }

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

        const val MATCHING_STATUS_WAITING_FOR_INSTRUCTOR = "WAITING_FOR_INSTRUCTOR"
        const val MATCHING_STATUS_WAITING_FOR_CONFIRMATION = "WAITING_FOR_CONFIRMATION"
        const val MATCHING_STATUS_PAYMENT_PENDING = "PAYMENT_PENDING"

        val NAVIGABLE_LESSON_STATUSES = setOf("CONFIRMED", "IN_PROGRESS")

        const val GENDER_MALE = "MALE"
    }
}
