package com.ssing.consumer

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
import com.ssing.presentation.consumerhome.navigation.ConsumerHome
import com.ssing.presentation.consumerprofile.navigation.ConsumerProfile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

@Stable
class ConsumerMainAppState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope,
) {
    val startDestination = ConsumerHome

    private val currentDestination = navController.currentBackStackEntryFlow
        .map { it.destination }
        .stateInWhileSubscribed(scope = coroutineScope, initialValue = null)

    val currentTab: StateFlow<ConsumerMainTab?> = currentDestination
        .map { destination ->
            ConsumerMainTab.find { route ->
                destination.matchRoute(route)
            }
        }
        .stateInWhileSubscribed(scope = coroutineScope, initialValue = null)

    private val _shouldShowBottomBar = MutableStateFlow(true)

    private val isMainTabRoute: StateFlow<Boolean> = currentDestination
        .map { destination ->
            ConsumerMainTab.contains { route ->
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

    fun navigate(tab: ConsumerMainTab) {
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
            ConsumerMainTab.HOME -> navController.navigate(ConsumerHome, navOptions)
            ConsumerMainTab.PROFILE -> navController.navigate(ConsumerProfile, navOptions)
            else -> {} // TODO: 추후 각 탭 화면 연결 예정
        }
    }

    fun updateBottomBarVisible(isVisible: Boolean) {
        _shouldShowBottomBar.value = isVisible
    }
}

@Composable
fun rememberConsumerMainAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): ConsumerMainAppState = remember(navController, coroutineScope) {
    ConsumerMainAppState(navController, coroutineScope)
}

private fun NavDestination?.matchRoute(route: Route): Boolean {
    val routeName = route::class.qualifiedName ?: return false
    return this?.hierarchy?.any { dest -> dest.route?.contains(routeName) == true } == true
}
