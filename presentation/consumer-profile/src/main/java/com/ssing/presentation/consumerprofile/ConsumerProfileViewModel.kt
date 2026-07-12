package com.ssing.presentation.consumerprofile

import androidx.lifecycle.viewModelScope
import com.ssing.core.ui.base.BaseViewModel
import com.ssing.data.auth.repository.api.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class ConsumerProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : BaseViewModel<ConsumerProfileContract.State, ConsumerProfileContract.Effect>(
    ConsumerProfileContract.State()
) {

    fun onLogoutClick() {
        if (uiState.value.isLoading) return

        updateState { copy(isLoading = true) }
        viewModelScope.launch {
            authRepository.postLogout()
                .onSuccess { sendEffect(ConsumerProfileContract.Effect.NavigateToLogin) }
                .onFailure {
                    Timber.e(it, "로그아웃 실패")
                    sendEffect(ConsumerProfileContract.Effect.ShowToast("로그아웃에 실패했습니다."))
                }

            updateState { copy(isLoading = false) }
        }
    }
}