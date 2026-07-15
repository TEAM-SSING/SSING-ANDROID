package com.ssing.presentation.consumerpayment

import androidx.compose.runtime.Immutable
import com.ssing.core.ui.common.component.Participant
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal interface PaymentContract {

    @Immutable
    data class State(
        val lessonId: Long = 0,
        val isLoading: Boolean = false,
        val showCancelModal: Boolean = false,
        val nickname: String = "",
        val tags: ImmutableList<String> = persistentListOf(),
        val classDateTime: String = "강사와 만난 뒤 강습 시작",
        val location: String = "",
        val duration: String = "",
        val participant: String = "",
        val participants: ImmutableList<Participant> = persistentListOf(),
        val equipmentStatus: String = "",
        val lessonCost: Int = 0,
        val resortCost: Int = 0,
        val totalPaymentAmount: Int = 0,
    )

    sealed interface Effect {
        data class NavigateToLesson(val lessonId: Long) : Effect
        data object NavigateToHome : Effect
        data class ShowToast(val message: String) : Effect
    }
}