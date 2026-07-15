package com.ssing.instructor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssing.core.network.token.TokenAccessManager
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
) : ViewModel() {

    private val _startDestination = MutableStateFlow<Route?>(null)
    val startDestination: StateFlow<Route?> = _startDestination.asStateFlow()

    init {
        viewModelScope.launch {
            val destination = when {
                START_AT_DEV_AUTH -> DevAuth
                tokenAccessManager.getAccessToken().isNullOrBlank() -> InstructorLogin
                else -> resolveLoggedInDestination()
            }
            _startDestination.update { destination }
            Timber.d("startDestination : $destination")
        }
    }

    private suspend fun resolveLoggedInDestination(): Route =
        instructorMatchingRepository.fetchActiveOffer()
            .fold(
                onSuccess = { offer ->
                    if (offer != null) {
                        InstructorMatching
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
