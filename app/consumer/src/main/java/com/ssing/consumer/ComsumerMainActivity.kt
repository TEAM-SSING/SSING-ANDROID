package com.ssing.consumer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssing.consumer.component.ConsumerBottomBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.collections.immutable.toImmutableList

@AndroidEntryPoint
class ComsumerMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val appState = rememberConsumerMainAppState()
            val isBottomBarVisible by appState.isBottomBarVisible.collectAsStateWithLifecycle()
            val currentTab by appState.currentTab.collectAsStateWithLifecycle()

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                bottomBar = {
                    ConsumerBottomBar(
                        isVisible = isBottomBarVisible,
                        tabs = ConsumerMainTab.entries.toImmutableList(),
                        currentTab = currentTab,
                        onTabSelected = appState::navigate,
                    )
                },
            ) { innerPadding ->
                ConsumerMainNavHost(
                    navController = appState.navController,
                    paddingValues = innerPadding,
                )
            }
        }
    }
}