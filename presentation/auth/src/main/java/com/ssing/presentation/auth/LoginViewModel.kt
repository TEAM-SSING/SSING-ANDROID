package com.ssing.presentation.auth

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.ssing.core.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class LoginViewModel @Inject constructor(
    private val kakaoLoginManager: KakaoLoginManager,
) :
    BaseViewModel<LoginContract.State, LoginContract.Effect>(
        LoginContract.State()
    ) {

    fun onLoginClick(context: Context) {
        viewModelScope.launch {
            kakaoLoginManager.login(context) { result ->
                viewModelScope.launch {
                    result.onSuccess { token ->
                        sendEffect(LoginContract.Effect.ShowToast("로그인 되었습니다."))
                        sendEffect(LoginContract.Effect.NavigateToHome)

                        // TODO: 서버 연결 시 token.accessToken 전달
                    }.onFailure { error ->
                        sendEffect(LoginContract.Effect.ShowToast("로그인에 실패했습니다."))

                        // TODO: 실패 처리
                    }
                }
            }
        }
    }
}
