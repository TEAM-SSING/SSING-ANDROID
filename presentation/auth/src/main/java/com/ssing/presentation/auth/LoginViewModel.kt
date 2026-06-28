package com.ssing.presentation.auth

import androidx.lifecycle.viewModelScope
import com.ssing.core.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class LoginViewModel @Inject constructor() :
    BaseViewModel<LoginContract.State, LoginContract.Effect>(
        LoginContract.State()
    ) {

    fun onLoginClick() {
        viewModelScope.launch {
            sendEffect(LoginContract.Effect.NavigateToHome)
        }
    }
}
