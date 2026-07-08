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
) {
    composable<Notification> {
        NotificationRoute(
            navigateBack = navController::popBackStack,
            //TODO 네비 구현 완료시 navController 넣기
            navigateToLessonArrival = { },
            navigateToMatchingLoading = { },
            navigateToLessonReady = { },
            modifier = Modifier.padding(paddingValues),
        )
    }
}

@Serializable
data object Notification : Route
