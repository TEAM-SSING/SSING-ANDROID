package com.ssing.presentation.auth

import androidx.compose.runtime.Immutable

internal interface LoginContract {

    @Immutable
    data class State(
        val isLoading: Boolean = false,
    )

    sealed interface Intent {
        data object OnKakaoClick : Intent
        data class OnKakaoSuccess(val token: String) : Intent
        data class OnKakaoFailure(val message: String) : Intent
    }

    sealed interface Effect {
        data object NavigateToHome : Effect
        data class ShowToast(val message: String) : Effect
        data object LaunchKakaoLogin : Effect
    }
}
