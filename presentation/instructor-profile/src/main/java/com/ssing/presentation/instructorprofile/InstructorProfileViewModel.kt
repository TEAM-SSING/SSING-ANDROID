package com.ssing.presentation.instructorprofile

import androidx.lifecycle.viewModelScope
import com.ssing.core.ui.base.BaseViewModel
import com.ssing.data.auth.repository.api.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class InstructorProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : BaseViewModel<InstructorProfileContract.State, InstructorProfileContract.Effect>(
    InstructorProfileContract.State()
) {

    fun onLogoutClick() {
        if (uiState.value.isLoading) return

        updateState { copy(isLoading = true) }
        viewModelScope.launch {
            authRepository.postLogout()
                .onSuccess {
                    Timber.d("🔐로그아웃 성공")
                    sendEffect(InstructorProfileContract.Effect.NavigateToLogin)
                }
                .onFailure {
                    Timber.e(it, "🔐로그아웃 실패")
                    sendEffect(InstructorProfileContract.Effect.ShowToast("로그아웃에 실패했습니다."))
                }

            updateState { copy(isLoading = false) }
        }
    }
}
