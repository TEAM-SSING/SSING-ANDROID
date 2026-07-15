package com.ssing.presentation.consumerpayment

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ssing.core.network.exception.ApiException
import com.ssing.core.ui.base.BaseViewModel
import com.ssing.core.ui.extension.uiMessage
import com.ssing.data.payment.model.PaymentSummary
import com.ssing.data.payment.repository.api.PaymentRepository
import com.ssing.presentation.consumerpayment.navigation.ConsumerPayment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class PaymentViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val paymentRepository: PaymentRepository,
) : BaseViewModel<PaymentContract.State, PaymentContract.Effect>(
    PaymentContract.State()
) {
    private val matchingRequestId = savedStateHandle.toRoute<ConsumerPayment>().matchingRequestId

    init {
        onPaymentClick()
        // TODO: /api/v1/consumer/matching-requests/{matchingRequestId}로 데이터 불러오기
    }

    fun onPaymentClick() {
        if (uiState.value.isLoading) return

        viewModelScope.launch {
            updateState {
                copy(isLoading = true)
            }

            paymentRepository.postPayment(matchingRequestId)
                .onSuccess { result ->
                    Timber.d("payment 응답: $result")
                    handlePaymentSuccess(result)
                }
                .onFailure { throwable ->
                    Timber.e(throwable, "payment 조회 실패")

                    updateState {
                        copy(isLoading = false)
                    }

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

//    fun navigateToLesson(lessonId: Long) =
//        sendEffect(PaymentContract.Effect.NavigateToLesson(lessonId))

    fun showCancelModal() =
        updateState { copy(showCancelModal = true) }

    fun closeCancelModal() =
        updateState { copy(showCancelModal = false) }

    fun confirmCancel() {
        updateState { copy(showCancelModal = false) }
        sendEffect(PaymentContract.Effect.NavigateToHome)
    }

    private companion object {
        const val MATCHING_STATUS_CONFIRMED = "CONFIRMED"
        const val MATCHING_STATUS_WAITING_FOR_OTHER_PAYMENTS = "WAITING_FOR_OTHER_PAYMENTS"
    }
}