package com.ssing.presentation.instructormatching

import androidx.lifecycle.viewModelScope
import com.ssing.core.network.exception.ApiException
import com.ssing.core.ui.base.BaseViewModel
import com.ssing.core.ui.extension.uiMessage
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
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class MatchingViewModel @Inject constructor(
    private val instructorMatchingRepository: InstructorMatchingRepository,
) :
    BaseViewModel<MatchingContract.State, MatchingContract.Effect>(
        MatchingContract.State()
    ) {

    init {
        loadMatchingExposure()
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

    /**
     * 현재 노출된 활성 매칭 제안을 REST로 조회해 화면을 복구한다.
     * WebSocket [MatchingPhase.OfferArrived] 이벤트를 놓쳤거나 화면 재진입 시 호출한다.
     */
    fun restoreActiveOffer() {
        viewModelScope.launch {
            instructorMatchingRepository.fetchActiveOffer()
                .onSuccess { offer ->
                    Timber.d("matching-offers 응답: $offer")
                    offer ?: return@onSuccess
                    updateState { copy(phase = MatchingPhase.OfferArrived(offer = offer.toUiModel())) }
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
        expiresAtMillis = null,
        nickname = requestSummary.requesterName,
        teamCount = requestSummary.matchingRequestCount,
        price = priceSummary.totalPaymentAmount,
        // TODO(#151, 기획 확인): classDateTime(강습 일시) — 즉시매칭(startType=IMMEDIATE)인데
        //  Figma엔 일시가 있고 계약(REST/WS)엔 없음. 즉시매칭에 이 시간이 왜 필요한지 기획에 확인 필요.
        classDateTime = "",
        // TODO(#151, 서버 대기): participants(수강생 나이/성별) —offer 상세 응답에 추가 예정.
        //  필드 내려오면 상세 API 매퍼에서 채워 연결.
        participants = emptyList(),
        lesson = LessonSummaryUiModel(
            resortLabel = lessonSummary.resort.displayName,
            sportLabel = lessonSummary.sport.toSportLabel(),
            levelLabel = lessonSummary.level.toLevelLabel(),
            headcount = lessonSummary.totalHeadcount,
            durationHours = lessonSummary.durationMinutes / 60,
        ),
    )

    private fun String.toSportLabel(): String =
        runCatching { SportOption.valueOf(this).label }.getOrDefault(this)

    private fun String.toLevelLabel(): String =
        runCatching { LevelOption.valueOf(this).label }.getOrDefault(this)

    private fun <T> Set<T>.toggle(item: T): Set<T> =
        if (item in this) this - item else this + item
}
