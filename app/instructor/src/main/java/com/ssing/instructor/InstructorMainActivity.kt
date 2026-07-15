package com.ssing.instructor

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ssing.core.network.session.AuthSessionManager
import com.ssing.core.network.token.TokenAccessManager
import com.ssing.core.ui.common.component.SsingBottomBar
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.presentation.auth.instructor.navigation.InstructorLogin
import com.ssing.presentation.devauth.navigation.DevAuth
import com.ssing.presentation.instructorhome.navigation.InstructorHome
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class InstructorMainActivity : ComponentActivity() {

    @Inject
    lateinit var authSessionManager: AuthSessionManager

    @Inject
    lateinit var tokenAccessManager: TokenAccessManager

    private val startDestination = MutableStateFlow<Any?>(null)

    private val requestNotificationPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            val msg = if (isGranted) "권한이 허용되었습니다." else "권한이 거부되었습니다."
            Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition { startDestination.value == null }
        enableEdgeToEdge()
        observeSessionExpired()
        requestNotificationPermission()
        decideStartDestination()
        setContent {
            SSINGTheme {
                val destination by startDestination.collectAsStateWithLifecycle()

                destination?.let { start ->
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
                            startDestination = start,
                        )
                    }
                }
            }
        }
    }

    private fun decideStartDestination() {
        if (START_AT_DEV_AUTH) {
            startDestination.value = DevAuth
            return
        }

        lifecycleScope.launch {
            val accessToken = tokenAccessManager.getAccessToken()
            startDestination.value =
                if (accessToken.isNullOrBlank()) InstructorLogin else InstructorHome
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(
                this, Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

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

    companion object {
        private const val START_AT_DEV_AUTH = false
    }
}
