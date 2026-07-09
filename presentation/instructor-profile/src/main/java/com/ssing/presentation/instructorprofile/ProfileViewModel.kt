package com.ssing.presentation.instructorprofile

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
        viewModelScope.launch {
            sendEffect(ProfileContract.Effect.NavigateToLogin)
        }
    }
}
