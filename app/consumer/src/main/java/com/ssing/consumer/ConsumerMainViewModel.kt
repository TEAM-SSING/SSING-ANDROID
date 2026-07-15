package com.ssing.consumer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssing.core.network.token.TokenAccessManager
import com.ssing.core.ui.navigation.Route
import com.ssing.data.matching.consumermatching.repository.api.ConsumerMatchingRepository
import com.ssing.presentation.consumerhome.navigation.ConsumerHome
import com.ssing.presentation.devauth.navigation.DevAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ConsumerMainViewModel @Inject constructor(
    private val tokenAccessManager: TokenAccessManager,
    private val consumerMatchingRepository: ConsumerMatchingRepository,
) : ViewModel() {

    private val _startDestination = MutableStateFlow<Route?>(null)
    val startDestination: StateFlow<Route?> = _startDestination.asStateFlow()

    private val _matchingRecoveryTarget = MutableStateFlow<Long?>(null)
    val matchingRecoveryTarget: StateFlow<Long?> = _matchingRecoveryTarget.asStateFlow()

    init {
        viewModelScope.launch {
            val hasToken = tokenAccessManager.getAccessToken() != null
            if (hasToken) {
                resolveLoggedInDestination()
            } else {
                _startDestination.update { DevAuth }
            }
            Timber.d("startDestination : ${_startDestination.value}, matchingRecoveryTarget : ${_matchingRecoveryTarget.value}")
        }
    }

    private suspend fun resolveLoggedInDestination() {
        consumerMatchingRepository.getMatchingActive()
            .onSuccess { matchingRequestId ->
                if (matchingRequestId != null) {
                    _matchingRecoveryTarget.update { matchingRequestId }
                }
            }
            .onFailure { throwable ->
                Timber.e(throwable, "활성 매칭 조회 실패 - 홈으로 폴백")
            }
        _startDestination.update { ConsumerHome }
    }
}
