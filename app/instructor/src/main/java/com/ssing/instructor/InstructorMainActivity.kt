package com.ssing.instructor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssing.instructor.component.InstructorBottomBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.collections.immutable.toImmutableList

@AndroidEntryPoint
class InstructorMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val appState = rememberInstructorMainAppState()
            val isBottomBarVisible by appState.isBottomBarVisible.collectAsStateWithLifecycle()
            val currentTab by appState.currentTab.collectAsStateWithLifecycle()

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                bottomBar = {
                    InstructorBottomBar(
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
