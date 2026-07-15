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
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ssing.core.network.session.AuthSessionManager
import com.ssing.core.ui.common.component.SsingBottomBar
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class InstructorMainActivity : ComponentActivity() {
    private val viewModel: InstructorMainViewModel by viewModels()

    @Inject
    lateinit var authSessionManager: AuthSessionManager

    private val deepLinkIntent = MutableStateFlow<Intent?>(null)

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
        enableEdgeToEdge()
        observeSessionExpired()
        requestNotificationPermission()

        splashScreen.setKeepOnScreenCondition { viewModel.startDestination.value == null }

        setContent {
            SSINGTheme {
                val startDestination by viewModel.startDestination.collectAsStateWithLifecycle()

                startDestination?.let { destination ->
                    val appState = rememberInstructorMainAppState()
                    val isBottomBarVisible by appState.isBottomBarVisible.collectAsStateWithLifecycle()
                    val currentTab by appState.currentTab.collectAsStateWithLifecycle()

                    LaunchedEffect(appState) {
                        deepLinkIntent.collect { intent ->
                            intent?.let {
                                appState.navController.handleDeepLink(it)
                                deepLinkIntent.value = null
                            }
                        }
                    }

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
                            startDestination = destination,
                        )
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        deepLinkIntent.value = intent
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
}
