package com.ssing.presentation.consumerpayment

import androidx.compose.runtime.Immutable
import com.ssing.core.ui.common.component.Participant
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal interface PaymentContract {

    @Immutable
    data class State(
        val isLoading: Boolean = false,
        val showCancelModal: Boolean = false,
        val nickname: String = "",
        val tags: ImmutableList<String> = persistentListOf(),
        val classDateTime: String = "",
        val location: String = "",
        val duration: String = "",
        val participants: ImmutableList<Participant> = persistentListOf(),
        val equipmentStatus: String = "",
        val lessonCost: Int = 0,
        val resortCost: Int = 0,
    )

    sealed interface Effect {
        data object NavigateToLesson : Effect
        data object NavigateToHome: Effect
        data class ShowToast(val message: String) : Effect
    }
}