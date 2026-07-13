package com.ssing.presentation.consumerprofile

import androidx.compose.runtime.Immutable

internal interface ConsumerProfileContract {

    @Immutable
    data class State(
        val isLoading: Boolean = false,
    )

    sealed interface Effect {
        data object NavigateToLogin : Effect
        data class ShowToast(val message: String) : Effect
    }
}
