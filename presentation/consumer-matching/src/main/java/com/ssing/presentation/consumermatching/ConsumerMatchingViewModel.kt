package com.ssing.presentation.consumermatching

import androidx.lifecycle.viewModelScope
import com.ssing.core.network.di.ApplicationScope
import com.ssing.core.network.socket.SocketState
import com.ssing.core.ui.base.BaseViewModel
import com.ssing.data.matching.consumermatching.event.ConsumerMatchingEvent
import com.ssing.data.matching.consumermatching.repository.api.ConsumerMatchingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ConsumerMatchingViewModel @Inject constructor(
    private val consumerMatchingRepository: ConsumerMatchingRepository,
    @param:ApplicationScope private val applicationScope: CoroutineScope,
) : BaseViewModel<ConsumerMatchingContract.State, ConsumerMatchingContract.Effect>(
    initialState = ConsumerMatchingContract.State(),
) {
    init {
        consumerMatchingRepository.connect()

        consumerMatchingRepository.event
            .onEach { event -> handleMatchingEvent(event) }
            .launchIn(viewModelScope)

        consumerMatchingRepository.socketState
            .onEach { state -> handleSocketState(state) }
            .launchIn(viewModelScope)
    }

    override fun onCleared() {
        applicationScope.launch {
            consumerMatchingRepository.disconnect()
        }
    }

    private fun handleMatchingEvent(event: ConsumerMatchingEvent) {
        when (event) {
            is ConsumerMatchingEvent.MatchingCanceledEvent -> onMatchingCanceled()
            is ConsumerMatchingEvent.MatchingStatusChangedEvent -> refetching(event.matchingRequestId)
            is ConsumerMatchingEvent.InstructorAcceptedEvent -> refetching(event.matchingRequestId)
            is ConsumerMatchingEvent.RequesterConfirmationUpdatedEvent -> refetching(event.matchingRequestId)
            is ConsumerMatchingEvent.PaymentPendingEvent -> refetching(event.matchingRequestId)
            is ConsumerMatchingEvent.PaymentStatusChangedEvent -> refetching(event.matchingRequestId)
            is ConsumerMatchingEvent.MatchingConfirmedEvent -> refetching(event.matchingRequestId)
            is ConsumerMatchingEvent.MatchingFailedEvent -> refetching(event.matchingRequestId)
        }
    }

    private fun handleSocketState(state: SocketState) {
        when (state) {
            is SocketState.Error, SocketState.Forbidden ->
                sendEffect(ConsumerMatchingContract.Effect.Pending.ShowToast("연결에 문제가 발생했어요."))

            SocketState.Connected, SocketState.Connecting, SocketState.Disconnected -> Unit
        }
    }

    private fun onMatchingCanceled() = viewModelScope.launch {
        consumerMatchingRepository.disconnect()
        sendEffect(ConsumerMatchingContract.Effect.Pending.NavigateToHome)
    }

    private fun refetching(matchingRequestId: Long) = viewModelScope.launch {
        // TODO: 소비자 매칭 상태 조회 API 연동 / matchingStatus로 화면 구성
        // WAITING_FOR_CONFIRMATION, WAITING_FOR_OTHER_CONFIRMATIONS
        //     -> Pending.NavigateToResult
        // PAYMENT_PENDING, WAITING_FOR_OTHER_PAYMENTS
        //     -> NavigationPayment
        // NO_AVAILABLE_INSTRUCTOR, FAILED, PAYMENT_EXPIRED
        //     -> consumerMatchingRepository.disconnect() 후 Pending.NavigateToFailure
        // CANCELED
        //     -> consumerMatchingRepository.disconnect() 후 Pending.NavigateToHome
    }

    // pending
    fun editCondition() = viewModelScope.launch {
        consumerMatchingRepository.disconnect()
        sendEffect(ConsumerMatchingContract.Effect.Pending.PopBackStack)
    }

    fun stopPending() = viewModelScope.launch {
        consumerMatchingRepository.disconnect()
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

    fun confirmCancel() = viewModelScope.launch {
        consumerMatchingRepository.disconnect()
        updateState { copy(showCancelModal = false) }
        sendEffect(ConsumerMatchingContract.Effect.Result.NavigateToHome)
    }

    fun requestRematching() {
        sendEffect(ConsumerMatchingContract.Effect.Result.PopBackStack)
    }

    fun acceptMatching() {
        // TODO: API 연동 시 실제 matchingRequestId 넣어주기
        sendEffect(ConsumerMatchingContract.Effect.Result.NavigateToPayment(1L))
    }

    fun navigateToReview() =
        sendEffect(ConsumerMatchingContract.Effect.Result.ShowToast("준비 중인 기능이에요."))

    // failure
    fun navigateToHome() =
        sendEffect(ConsumerMatchingContract.Effect.Failure.NavigateToHome)
}
