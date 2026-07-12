package com.ssing.presentation.auth.instructor

import com.ssing.core.ui.base.BaseViewModel
import com.ssing.presentation.auth.LoginContract
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class InstructorLoginViewModel @Inject constructor() :
    BaseViewModel<LoginContract.State, LoginContract.Effect>(
        LoginContract.State()
    ) {
    fun processIntent(intent: LoginContract.Intent) {
        when (intent) {
            LoginContract.Intent.OnKakaoClick -> {
                sendEffect(LoginContract.Effect.LaunchKakaoLogin)
            }

            is LoginContract.Intent.OnKakaoSuccess -> {
                sendEffect(LoginContract.Effect.ShowToast("로그인 되었습니다."))
                sendEffect(LoginContract.Effect.NavigateToHome)

                // TODO: 서버 연결 시 intent.token 전달
            }

            is LoginContract.Intent.OnKakaoFailure -> {
                sendEffect(LoginContract.Effect.ShowToast("로그인에 실패했습니다."))
            }
        }
    }
}