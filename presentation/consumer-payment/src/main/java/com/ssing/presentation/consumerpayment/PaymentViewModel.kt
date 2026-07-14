package com.ssing.presentation.consumerpayment

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.ssing.core.ui.base.BaseViewModel
import com.ssing.presentation.consumerpayment.navigation.ConsumerPayment
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class PaymentViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<PaymentContract.State, PaymentContract.Effect>(
    initialState = PaymentContract.State()
) {
    private val matchingRequestId = savedStateHandle.toRoute<ConsumerPayment>().matchingRequestId

    init {
        // TODO: /api/v1/consumer/matching-requests/{matchingRequestId}로 데이터 불러오기
    }

    fun navigateToLesson() =
        sendEffect(PaymentContract.Effect.NavigateToLesson)

    fun showCancelModal() =
        updateState { copy(showCancelModal = true) }

    fun closeCancelModal() =
        updateState { copy(showCancelModal = false) }

    fun confirmCancel() {
        updateState { copy(showCancelModal = false) }
        sendEffect(PaymentContract.Effect.NavigateToHome)
    }
}
