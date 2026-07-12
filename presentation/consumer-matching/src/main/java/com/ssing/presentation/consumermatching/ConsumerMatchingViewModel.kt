package com.ssing.presentation.consumermatching

import com.ssing.core.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class ConsumerMatchingViewModel @Inject constructor() :
    BaseViewModel<ConsumerMatchingContract.State, ConsumerMatchingContract.Effect>(
        initialState = ConsumerMatchingContract.State(),
    ) {
    // pending
    fun editCondition() {
        // TODO: 연결 해제
        sendEffect(ConsumerMatchingContract.Effect.Pending.PopBackStack)
    }

    fun stopPending() {
        // TODO: 연결 해제
        sendEffect(ConsumerMatchingContract.Effect.Pending.NavigateToHome)
    }

    // TODO: 소켓 연동 시 변경 / 플로우 확인용 임시 콜백
    fun navigateToResult() =
        sendEffect(ConsumerMatchingContract.Effect.Pending.NavigateToResult)

    // TODO: 소켓 연동 시 변경 / 플로우 확인용 임시 콜백
    fun navigateToFailure() =
        sendEffect(ConsumerMatchingContract.Effect.Pending.NavigateToFailure)

    // result
    fun showCancelModal() =
        updateState { copy(showCancelModal = true) }

    fun closeCancelModal() =
        updateState { copy(showCancelModal = false) }

    fun confirmCancel() {
        updateState { copy(showCancelModal = false) }
        sendEffect(ConsumerMatchingContract.Effect.Result.NavigateToHome)
    }

    fun requestRematching() {
        sendEffect(ConsumerMatchingContract.Effect.Result.PopBackStack)
    }

    fun acceptMatching() {
        sendEffect(ConsumerMatchingContract.Effect.Result.NavigateToPayment)
    }

    fun navigateToReview() =
        sendEffect(ConsumerMatchingContract.Effect.Result.ShowToast("준비 중인 기능이에요."))

    // failure
    fun navigateToHome() {
        // TODO: 연결 해제
        sendEffect(ConsumerMatchingContract.Effect.Failure.NavigateToHome)
    }
}
