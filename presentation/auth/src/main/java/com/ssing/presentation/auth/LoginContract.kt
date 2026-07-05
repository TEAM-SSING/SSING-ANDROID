package com.ssing.presentation.auth

import androidx.compose.runtime.Immutable

internal interface LoginContract {

    @Immutable
    data class State(
        val isLoading: Boolean = false,
    )

    sealed interface LoginIntent {
        data object OnKakaoLoginClick : LoginIntent
        data class OnKakaoLoginSuccess(val token: String) : LoginIntent
        data class OnKakaoLoginFailure(val message: String) : LoginIntent
    }

    sealed interface Effect {
        data object NavigateToHome : Effect
        data class ShowToast(val message: String) : Effect
        data object LaunchKakaoLogin : Effect
    }
}
