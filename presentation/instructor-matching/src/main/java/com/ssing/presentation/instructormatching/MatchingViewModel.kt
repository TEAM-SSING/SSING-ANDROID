package com.ssing.presentation.instructormatching

import androidx.lifecycle.viewModelScope
import com.ssing.core.network.di.ApplicationScope
import com.ssing.core.network.exception.ApiException
import com.ssing.core.ui.base.BaseViewModel
import com.ssing.core.ui.extension.uiMessage
import com.ssing.data.matching.instructormatching.model.InstructorMatchingOffer
import com.ssing.data.matching.instructormatching.model.InstructorMatchingOfferDetail
import com.ssing.data.matching.instructormatching.repository.api.InstructorMatchingRepository
import com.ssing.presentation.instructormatching.MatchingContract.MatchingDialog
import com.ssing.presentation.instructormatching.MatchingContract.MatchingPhase
import com.ssing.presentation.instructormatching.model.DurationOption
import com.ssing.presentation.instructormatching.model.LessonSummaryUiModel
import com.ssing.presentation.instructormatching.model.LevelOption
import com.ssing.presentation.instructormatching.model.MatchingOfferUiModel
import com.ssing.presentation.instructormatching.model.OfferStatusOption
import com.ssing.presentation.instructormatching.model.ParticipantUiModel
import com.ssing.presentation.instructormatching.model.SportOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
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
        observeSocketEvents()
    }

    private fun observeSocketEvents() {
        viewModelScope.launch {
            instructorMatchingRepository.socketEvents.collect { event ->
                Timber.d("matching 소켓 이벤트: ${event.eventType}")
                when (event.eventType) {
                    EVENT_OFFER_RECEIVED,
                    EVENT_OFFER_CLOSED,
                    EVENT_MATCHING_CANCELED,
                        -> {
                        val offerId = event.offerId
                        if (offerId != null) restoreOfferDetail(offerId) else restoreActiveOffer()
                    }
                }
            }
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
        updateState { copy(dialog = null, phase = MatchingPhase.SettingExposure) }
    }

    fun acceptOffer() {
        val offer = currentOffer() ?: return
        updateState {
            copy(
                phase = MatchingPhase.PendingConfirm(
                    offer = offer,
                    confirmationExpiresAtMillis = null
                )
            )
        }
    }

    fun rejectOffer() {
        updateState { copy(phase = MatchingPhase.Waiting) }
    }

    fun continueMatching() = updateState {
        copy(dialog = null, phase = MatchingPhase.Waiting)
    }

    fun retryMatching() {
        updateState { copy(dialog = null) }
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
    fun restoreOfferDetail(offerId: Long) {
        viewModelScope.launch {
            instructorMatchingRepository.fetchOfferDetail(offerId)
                .onSuccess { detail ->
                    Timber.d("matching-offer 상세 응답: $detail")
                    when (detail) {
                        is InstructorMatchingOfferDetail.Available ->
                            updateState { copy(phase = detail.toPhase()) }

                        is InstructorMatchingOfferDetail.Stale ->
                            // TODO(홈 연동): 홈 재조회 후 같은 offerId의 CONFIRMED/IN_PROGRESS 카드면 lessonId로 이동.
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
                    Timber.e(it, "matching-offer 상세 실패")
                    if (it is ApiException) {
                        sendEffect(MatchingContract.Effect.ShowToast(it.uiMessage))
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
        const val EVENT_OFFER_RECEIVED = "MATCHING_OFFER_RECEIVED"
        const val EVENT_OFFER_CLOSED = "MATCHING_OFFER_CLOSED"
        const val EVENT_MATCHING_CANCELED = "MATCHING_CANCELED"

        const val MATCHING_STATUS_WAITING_FOR_INSTRUCTOR = "WAITING_FOR_INSTRUCTOR"
        const val MATCHING_STATUS_WAITING_FOR_CONFIRMATION = "WAITING_FOR_CONFIRMATION"
        const val MATCHING_STATUS_PAYMENT_PENDING = "PAYMENT_PENDING"

        const val GENDER_MALE = "MALE"
    }
}
