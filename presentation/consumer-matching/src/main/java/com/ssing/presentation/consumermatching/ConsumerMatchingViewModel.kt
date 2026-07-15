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
import com.ssing.data.matching.consumermatching.model.ConsumerMatchingActive
import com.ssing.data.matching.consumermatching.repository.api.ConsumerMatchingRepository
import com.ssing.presentation.consumermatching.model.InstructorReview
import com.ssing.presentation.consumermatching.navigation.ConsumerMatchingGraph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.Year
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

        refetching(noneFallback = RecoveryNoneFallback.FAILURE)
        loadReview()
    }

    override fun onCleared() {
        applicationScope.launch {
            consumerMatchingRepository.disconnect()
        }
    }

    private fun loadReview() {
        updateState {
            copy(
                reviews = persistentListOf(
                    InstructorReview(
                        profileImageUrl = "",
                        nickname = "박OO",
                        gender = "남",
                        age = 42,
                        rating = 4,
                        tags = persistentListOf("스노보드", "처음 타요"),
                        content = "스노우 보드 처음 타는 저희 아이두명 강습 해주셨습니다.아이들이 겁이 많은데 정말 즐거운 시간 보낸 것 같습니다.",
                        date = "2026.12.11",
                    )
                ),
                totalReviewCount = 128,
            )
        }
    }

    private fun handleMatchingEvent(event: ConsumerMatchingEvent) {
        when (event) {
            is ConsumerMatchingEvent.MatchingCanceledEvent -> onMatchingCanceled()
            is ConsumerMatchingEvent.MatchingConfirmedEvent -> refetching(noneFallback = RecoveryNoneFallback.HOME)
            is ConsumerMatchingEvent.MatchingFailedEvent -> refetching(noneFallback = RecoveryNoneFallback.FAILURE)
            is ConsumerMatchingEvent.MatchingStatusChangedEvent -> refetching(noneFallback = RecoveryNoneFallback.FAILURE)
            is ConsumerMatchingEvent.InstructorAcceptedEvent -> refetching(noneFallback = RecoveryNoneFallback.FAILURE)
            is ConsumerMatchingEvent.RequesterConfirmationUpdatedEvent -> refetching(noneFallback = RecoveryNoneFallback.FAILURE)
            is ConsumerMatchingEvent.PaymentPendingEvent -> refetching(noneFallback = RecoveryNoneFallback.FAILURE)
            is ConsumerMatchingEvent.PaymentStatusChangedEvent -> refetching(noneFallback = RecoveryNoneFallback.FAILURE)
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

    private fun refetching(noneFallback: RecoveryNoneFallback) = viewModelScope.launch {
        consumerMatchingRepository.refetchMatching()
            .onSuccess { active ->
                when (active) {
                    is ConsumerMatchingActive.Active -> handleActiveMatching(active)
                    ConsumerMatchingActive.None -> handleNoneRecovery(noneFallback)
                }
            }
            .onFailure { throwable ->
                Timber.e(throwable, "매칭 상태 재조회 실패")
                sendEffect(ConsumerMatchingContract.Effect.Pending.ShowToast("매칭 상태를 불러오지 못했어요."))
            }
    }

    private suspend fun handleNoneRecovery(fallback: RecoveryNoneFallback) {
        consumerMatchingRepository.disconnect()
        val effect = when (fallback) {
            RecoveryNoneFallback.HOME -> ConsumerMatchingContract.Effect.Pending.NavigateToHome
            RecoveryNoneFallback.FAILURE -> ConsumerMatchingContract.Effect.Pending.NavigateToFailure
        }
        sendEffect(effect)
    }

    private fun handleActiveMatching(active: ConsumerMatchingActive.Active) {
        updateState { mergeFrom(active) }

        when (active.matchingStatus) {
            STATUS_WAITING_FOR_CONFIRMATION, STATUS_WAITING_FOR_OTHER_CONFIRMATIONS ->
                sendEffect(ConsumerMatchingContract.Effect.Pending.NavigateToResult)

            STATUS_PAYMENT_PENDING, STATUS_WAITING_FOR_OTHER_PAYMENTS ->
                sendEffect(ConsumerMatchingContract.Effect.Pending.NavigateToPayment(active.matchingRequestId))

            else -> Unit
        }
    }

    private fun ConsumerMatchingContract.State.mergeFrom(
        active: ConsumerMatchingActive.Active,
    ): ConsumerMatchingContract.State {
        val requestSummary = active.requestSummary
        val lessonSummary = active.lessonSummary
        val instructorProfile = active.instructorProfile
        val priceSummary = active.priceSummary

        return copy(
            tags = persistentListOf(
                requestSummary.sport.toSport(),
                requestSummary.lessonLevel.toLessonLevel()
            ),
            location = requestSummary.resort.displayName,
            duration = lessonSummary?.durationMinutes?.toDurationText() ?: duration,
            price = priceSummary?.totalPaymentAmount ?: price,
            name = instructorProfile?.name ?: name,
            age = instructorProfile?.birthYear?.toAge() ?: age,
            gender = instructorProfile?.gender?.toGenderText() ?: gender,
            level = instructorProfile?.level?.let { "grade$it" } ?: level,
            career = instructorProfile?.careerYears?.let { "${it}년" } ?: career,
            lessonCount = instructorProfile?.completedLessonCount?.let { "${it}회" } ?: lessonCount,
            rating = instructorProfile?.averageRating?.let { "%.1f".format(it) } ?: rating,
            introduction = instructorProfile?.introduction ?: introduction,
            certifications = instructorProfile?.certificateTypes?.toPersistentList()
                ?: certifications,
            estimatedFee = priceSummary?.totalPaymentAmount ?: estimatedFee,
            lessonDuration = lessonSummary?.durationMinutes?.toDurationText() ?: lessonDuration,
        )
    }

    private fun String.toSport(): String = when (this) {
        "SKI" -> "스키"
        "SNOWBOARD" -> "스노보드"
        else -> this
    }

    private fun String.toLessonLevel() = when (this) {
        "FIRST_TIME" -> "처음 타요"
        "BEGINNER" -> "1~5회 타봤어요"
        "INTERMEDIATE" -> "중급자에요"
        "CERTIFIED" -> "자격증이 있어요"
        else -> this
    }

    private fun Int.toAge(): Int = Year.now().value - this

    private fun Int.toDurationText(): String =
        when {
            this < 60 -> "${this}분"
            this % 60 == 0 -> "${this / 60}시간"
            else -> "${this / 60}시간 ${this % 60}분"
        }

    private fun String.toGenderText(): String = when (this) {
        "MALE" -> "남"
        "FEMALE" -> "여"
        else -> this
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
    fun navigateToHome() = launchExclusive {
        consumerMatchingRepository.cancelMatching(matchingRequestId)
            .onFailure { Timber.w(it, "실패 화면에서 매칭 중지 요청 실패 - 이미 종료된 매칭일 수 있음") }
        consumerMatchingRepository.disconnect()
        sendEffect(ConsumerMatchingContract.Effect.Failure.NavigateToHome)
    }

    private enum class RecoveryNoneFallback { HOME, FAILURE }

    private companion object {
        const val ACCEPTED_DECISION = "ACCEPTED"
        const val REJECTED_DECISION = "REJECTED"
        const val CANCEL_FAILURE_FALLBACK_MSG = "매칭 중지에 실패했어요."
        const val ACCEPT_FAILURE_FALLBACK_MSG = "강사 수락에 실패했어요."
        const val REJECT_FAILURE_FALLBACK_MSG = "강사 거절에 실패했어요."
        const val IN_DEVELOPMENT_MSG = "준비 중인 기능이에요."

        const val STATUS_WAITING_FOR_CONFIRMATION = "WAITING_FOR_CONFIRMATION"
        const val STATUS_WAITING_FOR_OTHER_CONFIRMATIONS = "WAITING_FOR_OTHER_CONFIRMATIONS"
        const val STATUS_PAYMENT_PENDING = "PAYMENT_PENDING"
        const val STATUS_WAITING_FOR_OTHER_PAYMENTS = "WAITING_FOR_OTHER_PAYMENTS"
    }
}
