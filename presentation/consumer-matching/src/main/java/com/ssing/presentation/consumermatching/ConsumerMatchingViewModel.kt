package com.ssing.presentation.consumermatching

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ssing.core.network.di.ApplicationScope
import com.ssing.core.network.exception.ApiException
import com.ssing.core.network.socket.SocketState
import com.ssing.core.ui.base.BaseViewModel
import com.ssing.core.ui.extension.uiMessage
import com.ssing.data.matching.consumermatching.event.ConsumerMatchingEvent
import com.ssing.data.matching.consumermatching.repository.api.ConsumerMatchingRepository
import com.ssing.presentation.consumermatching.navigation.ConsumerMatchingGraph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ConsumerMatchingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val consumerMatchingRepository: ConsumerMatchingRepository,
    @param:ApplicationScope private val applicationScope: CoroutineScope,
) : BaseViewModel<ConsumerMatchingContract.State, ConsumerMatchingContract.Effect>(
    initialState = ConsumerMatchingContract.State(),
) {
    private val matchingRequestId =
        savedStateHandle.toRoute<ConsumerMatchingGraph>().matchingRequestId

    private val isRecoveredEntry =
        savedStateHandle.toRoute<ConsumerMatchingGraph>().isRecoveredEntry

    private var isSubmitting = false

    private fun launchExclusive(block: suspend () -> Unit) {
        if (isSubmitting) return
        isSubmitting = true
        viewModelScope.launch {
            try {
                block()
            } finally {
                isSubmitting = false
            }
        }
    }

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
    fun editCondition() = launchExclusive {
        consumerMatchingRepository.cancelMatching(matchingRequestId)
            .onSuccess {
                consumerMatchingRepository.disconnect()
                val effect = if (isRecoveredEntry) {
                    ConsumerMatchingContract.Effect.Pending.NavigateToConditionFromRecovery
                } else {
                    ConsumerMatchingContract.Effect.Pending.PopBackStack
                }
                sendEffect(effect)
            }
            .onFailure {
                val message = if (it is ApiException) it.uiMessage else CANCEL_FAILURE_FALLBACK_MSG
                sendEffect(ConsumerMatchingContract.Effect.Pending.ShowToast(message))
            }
    }

    fun stopPending() = launchExclusive {
        consumerMatchingRepository.cancelMatching(matchingRequestId)
            .onSuccess {
                consumerMatchingRepository.disconnect()
                sendEffect(ConsumerMatchingContract.Effect.Pending.NavigateToHome)
            }
            .onFailure {
                val message = if (it is ApiException) it.uiMessage else CANCEL_FAILURE_FALLBACK_MSG
                sendEffect(ConsumerMatchingContract.Effect.Pending.ShowToast(message))
            }
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

    fun confirmCancel() = launchExclusive {
        consumerMatchingRepository.cancelMatching(matchingRequestId)
            .onSuccess {
                consumerMatchingRepository.disconnect()
                updateState { copy(showCancelModal = false) }
                sendEffect(ConsumerMatchingContract.Effect.Result.NavigateToHome)
            }
            .onFailure {
                updateState { copy(showCancelModal = false) }
                val message = if (it is ApiException) it.uiMessage else CANCEL_FAILURE_FALLBACK_MSG
                sendEffect(ConsumerMatchingContract.Effect.Result.ShowToast(message))
            }
    }

    fun requestRematching() = launchExclusive {
        consumerMatchingRepository.confirmMatching(
            matchingRequestId = matchingRequestId,
            decision = REJECTED_DECISION,
        ).onSuccess {
            sendEffect(ConsumerMatchingContract.Effect.Result.PopBackStack)
        }.onFailure {
            val message = if (it is ApiException) it.uiMessage else REJECT_FAILURE_FALLBACK_MSG
            sendEffect(ConsumerMatchingContract.Effect.Result.ShowToast(message))
        }
    }

    fun acceptMatching() = launchExclusive {
        consumerMatchingRepository.confirmMatching(
            matchingRequestId = matchingRequestId,
            decision = ACCEPTED_DECISION,
        ).onSuccess {
            sendEffect(ConsumerMatchingContract.Effect.Result.NavigateToPayment(matchingRequestId))
        }.onFailure {
            val message = if (it is ApiException) it.uiMessage else ACCEPT_FAILURE_FALLBACK_MSG
            sendEffect(ConsumerMatchingContract.Effect.Result.ShowToast(message))
        }
    }

    fun navigateToReview() =
        sendEffect(ConsumerMatchingContract.Effect.Result.ShowToast(IN_DEVELOPMENT_MSG))

    // failure
    fun navigateToHome() =
        sendEffect(ConsumerMatchingContract.Effect.Failure.NavigateToHome)

    private companion object {
        const val ACCEPTED_DECISION = "ACCEPTED"
        const val REJECTED_DECISION = "REJECTED"
        const val CANCEL_FAILURE_FALLBACK_MSG = "매칭 중지에 실패했어요."
        const val ACCEPT_FAILURE_FALLBACK_MSG = "강사 수락에 실패했어요."
        const val REJECT_FAILURE_FALLBACK_MSG = "강사 거절에 실패했어요."
        const val IN_DEVELOPMENT_MSG = "준비 중인 기능이에요."
    }
}
