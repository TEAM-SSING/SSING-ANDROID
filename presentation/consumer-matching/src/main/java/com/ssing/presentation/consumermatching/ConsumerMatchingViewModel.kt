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

    fun closeCancelModal() =
        updateState { copy(showCancelModal = false) }

    fun confirmCancel() {
        updateState { copy(showCancelModal = false) }
        sendEffect(ConsumerMatchingContract.Effect.Result.NavigateToHome)
    }

    fun abortCancel() =
        updateState { copy(showCancelModal = false) }

    fun requestRematching() {
        sendEffect(ConsumerMatchingContract.Effect.Result.PopBackStack)
    }

    fun acceptMatching() {
        sendEffect(ConsumerMatchingContract.Effect.Result.NavigateToPayment)
    }

    fun navigateToReview() =
        sendEffect(ConsumerMatchingContract.Effect.Result.ShowToast("준비 중인 기능이에요."))
}
