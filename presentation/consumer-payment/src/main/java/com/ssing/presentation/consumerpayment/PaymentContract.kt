package com.ssing.presentation.consumerpayment

import androidx.compose.runtime.Immutable

internal interface PaymentContract {

    @Immutable
    data class State(
        val isLoading: Boolean = false,
    )

    sealed interface Effect {
        data class ShowToast(val message: String) : Effect
        data object NavigateToLesson : Effect
    }
}
