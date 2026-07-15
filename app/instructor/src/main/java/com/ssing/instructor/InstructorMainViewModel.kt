package com.ssing.instructor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssing.core.network.token.TokenAccessManager
import com.ssing.core.notification.NotificationTokenProvider
import com.ssing.core.ui.navigation.Route
import com.ssing.data.matching.instructormatching.repository.api.InstructorMatchingRepository
import com.ssing.presentation.auth.instructor.navigation.InstructorLogin
import com.ssing.presentation.devauth.navigation.DevAuth
import com.ssing.presentation.instructorhome.navigation.InstructorHome
import com.ssing.presentation.instructormatching.navigation.InstructorMatching
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class InstructorMainViewModel @Inject constructor(
    private val tokenAccessManager: TokenAccessManager,
    private val instructorMatchingRepository: InstructorMatchingRepository,
    private val notificationTokenProvider: NotificationTokenProvider,
) : ViewModel() {

    private val _startDestination = MutableStateFlow<Route?>(null)
    val startDestination: StateFlow<Route?> = _startDestination.asStateFlow()

    init {
        viewModelScope.launch {
            val destination = when {
                START_AT_DEV_AUTH -> DevAuth
                tokenAccessManager.getAccessToken().isNullOrBlank() -> InstructorLogin
                else -> {
                    registerFcmToken()
                    resolveLoggedInDestination()
                }
            }
            _startDestination.update { destination }
            Timber.d("startDestination : $destination")
        }
    }

    private fun registerFcmToken() {
        viewModelScope.launch {
            notificationTokenProvider.registerCurrentToken()
        }
    }

    /**
     * 로그인 상태의 시작 지점: 활성 제안이 있거나 노출 중이면 매칭 화면, 아니면 홈.
     * 활성 매칭 조회 실패 시에도 홈으로 폴백한다.
     */
    private suspend fun resolveLoggedInDestination(): Route =
        instructorMatchingRepository.fetchMatchingActive()
            .fold(
                onSuccess = { active ->
                    if (active.offerId != null || active.setting.isExposed) {
                        InstructorMatching()
                    } else {
                        InstructorHome
                    }
                },
                onFailure = { throwable ->
                    Timber.e(throwable, "활성 매칭 조회 실패 - 홈으로 폴백")
                    InstructorHome
                },
            )

    companion object {
        private const val START_AT_DEV_AUTH = false
    }
}
