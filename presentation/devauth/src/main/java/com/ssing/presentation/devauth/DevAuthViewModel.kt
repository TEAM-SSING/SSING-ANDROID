package com.ssing.presentation.devauth

import androidx.lifecycle.viewModelScope
import com.ssing.core.ui.base.BaseViewModel
import com.ssing.data.devauth.repository.api.DevAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class DevAuthViewModel @Inject constructor(
    private val devAuthRepository: DevAuthRepository,
) : BaseViewModel<DevAuthContract.State, DevAuthContract.Effect>(
    DevAuthContract.State()
) {

    init {
        loadPersonaKeys()
    }

    private fun loadPersonaKeys() {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }

            devAuthRepository.getPersonaKeys()
                .onSuccess { personaKeys ->
                    updateState { copy(isLoading = false, personaKeys = personaKeys) }
                }
                .onFailure {
                    updateState { copy(isLoading = false) }
                    sendEffect(DevAuthContract.Effect.ShowToast("페르소나 목록을 불러오지 못했습니다."))
                }
        }
    }

    fun setPersonaKey(key: String) {
        updateState { copy(selectedPersonaKey = key) }
    }

    fun login() {
        if (uiState.value.isLoading) return
        val personaKey = uiState.value.selectedPersonaKey ?: return

        viewModelScope.launch {
            updateState { copy(isLoading = true) }

            devAuthRepository.personaLogin(personaKey = personaKey, autoCreate = false)
                .onSuccess {
                    updateState { copy(isLoading = false) }
                    sendEffect(DevAuthContract.Effect.NavigateToHome)
                }
                .onFailure {
                    updateState { copy(isLoading = false) }
                    sendEffect(DevAuthContract.Effect.ShowToast("로그인에 실패했습니다."))
                }
        }
    }
}
