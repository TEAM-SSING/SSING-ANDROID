package com.ssing.presentation.auth.instructor

import androidx.lifecycle.viewModelScope
import com.ssing.core.ui.base.BaseViewModel
import com.ssing.data.auth.repository.api.AuthRepository
import com.ssing.presentation.auth.LoginContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class InstructorLoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : BaseViewModel<LoginContract.State, LoginContract.Effect>(
    LoginContract.State()
) {
    fun processIntent(intent: LoginContract.Intent) {
        when (intent) {
            LoginContract.Intent.OnKakaoClick -> {
                sendEffect(LoginContract.Effect.LaunchKakaoLogin)
            }

            is LoginContract.Intent.OnKakaoSuccess -> {
                if (uiState.value.isLoading) return
                updateState { copy(isLoading = true) }
                viewModelScope.launch {
                    authRepository.postInstructorKakaoAuth(intent.token)
                        .onSuccess {
                            Timber.d( "🔐강사 로그인 성공")
                            sendEffect(LoginContract.Effect.NavigateToHome) }
                        .onFailure {
                            Timber.e(it, "🔐강사 로그인 실패")
                            sendEffect(LoginContract.Effect.ShowToast("로그인에 실패했습니다."))
                        }
                    updateState { copy(isLoading = false) }
                }
            }

            is LoginContract.Intent.OnKakaoFailure -> {
                sendEffect(LoginContract.Effect.ShowToast("로그인에 실패했습니다."))
            }
        }
    }
}
