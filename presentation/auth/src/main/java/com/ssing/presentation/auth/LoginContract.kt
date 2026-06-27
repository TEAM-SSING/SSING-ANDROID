package com.ssing.presentation.auth

import androidx.compose.runtime.Immutable

internal interface LoginContract {

    @Immutable
    data class State(
        val isLoading: Boolean = false,
    )

    sealed interface Effect {
        data object NavigateToConsumerHome : Effect
        data object NavigateToInstructorHome : Effect
        data class ShowToast(val message: String) : Effect
    }
}
