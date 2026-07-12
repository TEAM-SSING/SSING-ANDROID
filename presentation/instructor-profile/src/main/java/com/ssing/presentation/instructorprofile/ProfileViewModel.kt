package com.ssing.presentation.instructorprofile

import androidx.lifecycle.viewModelScope
import com.ssing.core.ui.base.BaseViewModel
import com.ssing.data.auth.repository.api.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : BaseViewModel<ProfileContract.State, ProfileContract.Effect>(
    ProfileContract.State()
) {

    fun onLogoutClick() {
        if (uiState.value.isLoading) return

        updateState { copy(isLoading = true) }
        viewModelScope.launch {
            authRepository.postLogout()
                .onSuccess { sendEffect(ProfileContract.Effect.NavigateToLogin) }
                .onFailure {
                    Timber.e(it, "로그아웃 실패")
                    sendEffect(ProfileContract.Effect.ShowToast("로그아웃에 실패했습니다."))
                }

            updateState { copy(isLoading = false) }
        }
    }
}