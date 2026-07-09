package com.ssing.presentation.instructorprofile

import androidx.compose.runtime.Immutable

internal interface ProfileContract {

    @Immutable
    data class State(
        val isLoading: Boolean = false,
    )

    sealed interface Effect {
        data object NavigateToLogin : Effect
        data class ShowToast(val message: String) : Effect
    }
}
