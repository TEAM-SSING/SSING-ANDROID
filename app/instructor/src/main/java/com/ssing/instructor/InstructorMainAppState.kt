package com.ssing.instructor

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.ssing.core.ui.extension.stateInWhileSubscribed
import com.ssing.core.ui.navigation.Route
import com.ssing.presentation.instructorhome.navigation.InstructorHome
import com.ssing.presentation.instructorprofile.navigation.InstructorProfile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

@Stable
class InstructorMainAppState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope,
) {
    val startDestination = InstructorHome

    private val currentDestination = navController.currentBackStackEntryFlow
        .map { it.destination }
        .stateInWhileSubscribed(scope = coroutineScope, initialValue = null)

    val currentTab: StateFlow<InstructorMainTab?> = currentDestination
        .map { destination ->
            InstructorMainTab.find { route ->
                destination.matchRoute(route)
            }
        }
        .stateInWhileSubscribed(scope = coroutineScope, initialValue = null)

    private val _shouldShowBottomBar = MutableStateFlow(true)

    private val isMainTabRoute: StateFlow<Boolean> = currentDestination
        .map { destination ->
            InstructorMainTab.contains { route ->
                destination.matchRoute(route)
            }
        }
        .stateInWhileSubscribed(scope = coroutineScope, initialValue = false)

    val isBottomBarVisible: StateFlow<Boolean> = combine(
        isMainTabRoute,
        _shouldShowBottomBar,
    ) { isMainTab, shouldShow ->
        isMainTab && shouldShow
    }.stateInWhileSubscribed(scope = coroutineScope, initialValue = false)

    fun navigate(tab: InstructorMainTab) {
        val navOptions = navOptions {
            navController.currentDestination?.route?.let { currentRoute ->
                popUpTo(currentRoute) {
                    saveState = true
                    inclusive = true
                }
            }
            launchSingleTop = true
            restoreState = true
        }
        when (tab) {
            InstructorMainTab.HOME -> navController.navigate(InstructorHome, navOptions)
            InstructorMainTab.PROFILE -> navController.navigate(InstructorProfile, navOptions)
            else -> {} // TODO: 추후 각 탭 화면 연결 예정
        }
    }

    fun updateBottomBarVisible(isVisible: Boolean) {
        _shouldShowBottomBar.value = isVisible
    }
}

@Composable
fun rememberInstructorMainAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): InstructorMainAppState = remember(navController, coroutineScope) {
    InstructorMainAppState(navController, coroutineScope)
}

private fun NavDestination?.matchRoute(route: Route): Boolean {
    val routeName = route::class.qualifiedName ?: return false
    return this?.hierarchy?.any { dest -> dest.route?.contains(routeName) == true } == true
}
