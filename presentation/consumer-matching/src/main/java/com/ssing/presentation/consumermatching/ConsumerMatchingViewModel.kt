package com.ssing.presentation.consumermatching

import com.ssing.core.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class ConsumerMatchingViewModel @Inject constructor() :
    BaseViewModel<ConsumerMatchingContract.State, ConsumerMatchingContract.Effect>(
        initialState = ConsumerMatchingContract.State(),
    ) {
    fun showCancelModal() =
        updateState { copy(showCancelModal = true) }

    fun closeCancelModel() =
        updateState { copy(showCancelModal = false) }

    fun confirmCancel() {
        updateState { copy(showCancelModal = false) }
        sendEffect(ConsumerMatchingContract.Effect.ResultEffect.NavigateToHome)
    }

    fun abortCancel() =
        updateState { copy(showCancelModal = false) }

    fun requsetRematching() {
        sendEffect(ConsumerMatchingContract.Effect.ResultEffect.PopBackStack)
    }

    fun acceptMatching() {
        sendEffect(ConsumerMatchingContract.Effect.ResultEffect.NavigateToPayment)
    }

    fun navigateToReview() =
        sendEffect(ConsumerMatchingContract.Effect.ResultEffect.ShowToast("준비 중인 기능이에요."))
}
