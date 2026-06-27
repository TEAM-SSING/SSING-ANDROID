package com.ssing.core.ui.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController

/**
 * 공용 뷰모델을 불러오기 위한 함수
 * */
@Composable
inline fun <reified T : ViewModel> sharedViewModel( 
    backStackEntry: NavBackStackEntry,
    navController: NavHostController,
): T {
    val navGraphRoute = backStackEntry.destination.parent?.route ?: return hiltViewModel()
    val parentEntry =
        remember(backStackEntry) {
            navController.getBackStackEntry(navGraphRoute)
        }
    return hiltViewModel(parentEntry)
}
