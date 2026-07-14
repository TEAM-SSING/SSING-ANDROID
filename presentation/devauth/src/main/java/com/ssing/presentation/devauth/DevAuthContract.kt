package com.ssing.presentation.devauth

import androidx.compose.runtime.Immutable

internal interface DevAuthContract {

    @Immutable
    data class State(
        val isLoading: Boolean = false,
        val personaKeys: List<String> = emptyList(),
        val selectedPersonaKey: String? = null,
    )

    sealed interface Effect {
        data object NavigateToHome : Effect
        data class ShowToast(val message: String) : Effect
    }
}