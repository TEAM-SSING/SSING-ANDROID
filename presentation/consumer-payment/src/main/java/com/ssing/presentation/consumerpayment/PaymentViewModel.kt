package com.ssing.presentation.consumerpayment

import androidx.lifecycle.viewModelScope
import com.ssing.core.network.exception.ApiException
import com.ssing.core.ui.base.BaseViewModel
import com.ssing.core.ui.common.component.Gender
import com.ssing.core.ui.common.component.Participant
import com.ssing.core.ui.extension.uiMessage
import com.ssing.data.matching.consumermatching.model.ConsumerMatchingActive
import com.ssing.data.matching.consumermatching.model.ConsumerMatchingSummaryParticipant
import com.ssing.data.matching.consumermatching.repository.api.ConsumerMatchingRepository
import com.ssing.data.payment.model.PaymentSummary
import com.ssing.data.payment.repository.api.PaymentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class PaymentViewModel @Inject constructor(
    private val paymentRepository: PaymentRepository,
    private val consumerMatchingRepository: ConsumerMatchingRepository,
) : BaseViewModel<PaymentContract.State, PaymentContract.Effect>(
    PaymentContract.State()
) {
    private var matchingRequestId: Long? = null

    init {
        loadPaymentInfo()
    }

    private fun loadPaymentInfo() = viewModelScope.launch {
        consumerMatchingRepository.refetchMatching()
            .onSuccess { active ->
                if (active !is ConsumerMatchingActive.Active) {
                    return@onSuccess Timber.w("결제 화면 진입했지만 활성 매칭이 없음")
                }

                matchingRequestId = active.matchingRequestId

                val requestSummary = active.requestSummary
                val lessonSummary = active.lessonSummary
                val priceSummary = active.priceSummary

                updateState {
                    copy(
                        nickname = requestSummary.requesterName,
                        tags = persistentListOf(
                            requestSummary.sport.toSport(),
                            requestSummary.lessonLevel.toLessonLevel()
                        ),
                        location = requestSummary.resort.displayName,
                        duration = lessonSummary?.durationMinutes?.toDurationText() ?: duration,
                        participants = requestSummary.participants.map { it.toParticipant() }
                            .toPersistentList(),
                        lessonCost = priceSummary?.lessonPriceAmount ?: lessonCost,
                        resortCost = priceSummary?.resortPassFeeAmount ?: resortCost,
                        totalPaymentAmount = priceSummary?.totalPaymentAmount ?: totalPaymentAmount,
                        equipmentStatus = "착용 완료",
                    )
                }
            }
            .onFailure { throwable ->
                Timber.e(throwable, "결제 화면 데이터 조회 실패")
            }
    }

    private fun String.toSport(): String = when (this) {
        "SKI" -> "스키"
        "SNOWBOARD" -> "스노보드"
        else -> this
    }

    private fun String.toLessonLevel(): String = when (this) {
        "FIRST_TIME" -> "처음 타요"
        "BEGINNER" -> "1~5회 타봤어요"
        "INTERMEDIATE" -> "중급자에요"
        "CERTIFIED" -> "자격증이 있어요"
        else -> this
    }

    private fun Int.toDurationText(): String =
        when {
            this < 60 -> "${this}분"
            this % 60 == 0 -> "${this / 60}시간"
            else -> "${this / 60}시간 ${this % 60}분"
        }

    private fun ConsumerMatchingSummaryParticipant.toParticipant(): Participant =
        Participant(age = age, gender = gender.toGender())

    private fun String.toGender(): Gender = when (this) {
        "MALE" -> Gender.MALE
        "FEMALE" -> Gender.FEMALE
        else -> Gender.MALE
    }

    fun onPaymentClick() {
        if (uiState.value.isLoading) return
        val matchingRequestId = matchingRequestId ?: run {
            sendEffect(PaymentContract.Effect.ShowToast("결제 정보를 아직 불러오는 중이에요. 잠시 후 다시 시도해주세요."))
            return
        }

        updateState { copy(isLoading = true) }

        viewModelScope.launch {
            paymentRepository.postPayment(matchingRequestId)
                .onSuccess { result ->
                    Timber.d("payment 응답: $result")
                    handlePaymentSuccess(result)
                }
                .onFailure { throwable ->
                    Timber.e(throwable, "payment 조회 실패")

                    updateState { copy(isLoading = false) }

                    if (throwable is ApiException) {
                        sendEffect(PaymentContract.Effect.ShowToast(throwable.uiMessage))
                    }
                }
        }
    }

    private fun handlePaymentSuccess(summary: PaymentSummary) {
        when (summary.matchingStatus) {
            MATCHING_STATUS_CONFIRMED -> {
                val lessonId = summary.lessonId
                if (lessonId != null) {
                    updateState { copy(isLoading = false) }
                    sendEffect(PaymentContract.Effect.NavigateToLesson(lessonId))
                } else {
                    Timber.e("CONFIRMED 응답인데 lessonId 누락: $summary")
                    updateState { copy(isLoading = false) }
                    sendEffect(
                        PaymentContract.Effect.ShowToast("결제는 완료되었지만 강습 정보를 불러오지 못했습니다.")
                    )
                }
            }

            MATCHING_STATUS_WAITING_FOR_OTHER_PAYMENTS -> {
                updateState { copy(isLoading = false) }
                sendEffect(
                    PaymentContract.Effect.ShowToast("결제가 완료되었어요. 다른 참여자의 결제를 기다리고 있어요.")
                )
            }

            else -> {
                Timber.e("알 수 없는 matchingStatus: ${summary.matchingStatus}")
                updateState { copy(isLoading = false) }
                sendEffect(PaymentContract.Effect.ShowToast("알 수 없는 매칭 상태입니다."))
            }
        }
    }

    fun showCancelModal() =
        updateState { copy(showCancelModal = true) }

    fun closeCancelModal() =
        updateState { copy(showCancelModal = false) }

    fun confirmCancel() {
        val matchingRequestId = matchingRequestId ?: run {
            updateState { copy(showCancelModal = false) }
            sendEffect(PaymentContract.Effect.ShowToast("매칭 정보를 아직 불러오는 중이에요. 잠시 후 다시 시도해주세요."))
            return
        }

        updateState { copy(showCancelModal = false) }

        viewModelScope.launch {
            consumerMatchingRepository.cancelMatching(matchingRequestId)
                .onSuccess {
                    sendEffect(PaymentContract.Effect.NavigateToHome)
                }
                .onFailure { throwable ->
                    if (throwable is ApiException) sendEffect(
                        PaymentContract.Effect.ShowToast(
                            throwable.uiMessage
                        )
                    )
                }
        }
    }

    private companion object {
        const val MATCHING_STATUS_CONFIRMED = "CONFIRMED"
        const val MATCHING_STATUS_WAITING_FOR_OTHER_PAYMENTS = "WAITING_FOR_OTHER_PAYMENTS"
    }
}