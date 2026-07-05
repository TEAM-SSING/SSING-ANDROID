package com.ssing.presentation.auth

import com.ssing.core.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class LoginViewModel @Inject constructor() :
    BaseViewModel<LoginContract.State, LoginContract.Effect>(
        LoginContract.State()
    ) {

    fun processIntent(intent: LoginContract.LoginIntent) {
        when (intent) {
            LoginContract.LoginIntent.OnKakaoLoginClick -> {
                sendEffect(LoginContract.Effect.LaunchKakaoLogin)
            }

            is LoginContract.LoginIntent.OnKakaoLoginSuccess -> {
                sendEffect(LoginContract.Effect.ShowToast("로그인 되었습니다."))
                sendEffect(LoginContract.Effect.NavigateToHome)

                // TODO: 서버 연결 시 intent.token 전달
            }

            is LoginContract.LoginIntent.OnKakaoLoginFailure -> {
                sendEffect(LoginContract.Effect.ShowToast("로그인에 실패했습니다."))
            }
        }
    }
}
