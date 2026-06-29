package com.ssing.presentation.consumerhome

import androidx.compose.runtime.Immutable

internal interface ConsumerHomeContract {

    @Immutable
    data class State(
        val isLoading: Boolean = false,
    )

    sealed interface Effect {
        data class ShowToast(val message: String) : Effect
        data object NavigateToMatching : Effect
    }
}
