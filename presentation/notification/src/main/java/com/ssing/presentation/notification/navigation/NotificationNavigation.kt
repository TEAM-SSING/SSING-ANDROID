package com.ssing.presentation.notification.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ssing.core.ui.navigation.Route
import com.ssing.presentation.notification.NotificationRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToNotification(navOptions: NavOptions? = null) {
    navigate(route = Notification, navOptions = navOptions)
}

fun NavGraphBuilder.notificationNavGraph(
    paddingValues: PaddingValues,
    navController: NavController,
    navigateToLessonArrival: () -> Unit,
    navigateToMatchingLoading: () -> Unit,
    navigateToLessonReady: () -> Unit,
) {
    composable<Notification> {
        NotificationRoute(
            navigateBack = { navController.popBackStack() },
            navigateToLessonArrival = navigateToLessonArrival,
            navigateToMatchingLoading = navigateToMatchingLoading,
            navigateToLessonReady = navigateToLessonReady,
            modifier = Modifier.padding(paddingValues),
        )
    }
}

@Serializable
data object Notification : Route
