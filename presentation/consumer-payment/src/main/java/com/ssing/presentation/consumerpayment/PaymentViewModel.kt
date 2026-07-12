package com.ssing.presentation.consumerpayment

import com.ssing.core.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class PaymentViewModel @Inject constructor() :
    BaseViewModel<PaymentContract.State, PaymentContract.Effect>(
        initialState = PaymentContract.State()
    ) {

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
