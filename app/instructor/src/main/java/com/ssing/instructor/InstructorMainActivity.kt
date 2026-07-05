package com.ssing.instructor

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ssing.core.network.session.AuthSessionManager
import com.ssing.core.ui.common.component.SsingBottomBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class InstructorMainActivity : ComponentActivity() {

    @Inject
    lateinit var authSessionManager: AuthSessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeSessionExpired()
        setContent {
            SSINGTheme {
                val appState = rememberInstructorMainAppState()
                val isBottomBarVisible by appState.isBottomBarVisible.collectAsStateWithLifecycle()
                val currentTab by appState.currentTab.collectAsStateWithLifecycle()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        SsingBottomBar(
                            isVisible = isBottomBarVisible,
                            tabs = InstructorMainTab.entries.toImmutableList(),
                            currentTab = currentTab,
                            onTabSelected = appState::navigate,
                        )
                    },
                ) { innerPadding ->
                    InstructorMainNavHost(
                        navController = appState.navController,
                        paddingValues = innerPadding,
                    )
                }
            }
        }
    }

    /**
     * 세션 만료(refresh token 만료) 이벤트 구독.
     * 토큰은 이미 clear된 상태이므로 앱을 재시작하면
     * startDestination 결정 로직이 자연스럽게 로그인 화면으로 보낸다.
     */
    private fun observeSessionExpired() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                authSessionManager.sessionExpired.collect {
                    val intent = Intent(
                        this@InstructorMainActivity,
                        InstructorMainActivity::class.java,
                    ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                }
            }
        }
    }
}
