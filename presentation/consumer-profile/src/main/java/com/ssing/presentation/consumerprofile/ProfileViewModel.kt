package com.ssing.presentation.consumerprofile

import androidx.lifecycle.viewModelScope
import com.ssing.core.ui.base.BaseViewModel
import com.ssing.data.auth.repository.api.LogoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class ProfileViewModel @Inject constructor(
    private val logoutRepository: LogoutRepository,
) : BaseViewModel<ProfileContract.State, ProfileContract.Effect>(
    ProfileContract.State()
) {

    fun onLogoutClick() {
        if (uiState.value.isLoading) return

        updateState { copy(isLoading = true) }
        viewModelScope.launch {
            logoutRepository.logout()
                .onSuccess { sendEffect(ProfileContract.Effect.NavigateToLogin) }
                .onFailure {
                    Timber.e(it, "로그아웃 실패")
                    sendEffect(ProfileContract.Effect.ShowToast("로그아웃에 실패했습니다."))
                }

            updateState { copy(isLoading = false) }
        }
    }
}
