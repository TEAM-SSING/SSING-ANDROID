package com.ssing.presentation.instructorhome

import androidx.compose.runtime.Immutable

internal interface InstructorHomeContract {

    @Immutable
    data class State(
        val isLoading: Boolean = false,
    )

    sealed interface Effect {
        data class ShowToast(val message: String) : Effect
        data object NavigateToMatching : Effect
    }
}
