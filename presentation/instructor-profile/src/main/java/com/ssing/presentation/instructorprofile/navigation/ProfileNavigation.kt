package com.ssing.presentation.instructorprofile.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ssing.core.ui.extension.clearBackStackNavOptions
import com.ssing.core.ui.navigation.MainTabRoute
import com.ssing.presentation.auth.navigation.Login
import com.ssing.presentation.instructorprofile.ProfileRoute
import kotlinx.serialization.Serializable

@Serializable
data object Profile : MainTabRoute

fun NavGraphBuilder.profileNavGraph(
    paddingValues: PaddingValues,
    navController: NavController,
) {
    composable<Profile> {
        ProfileRoute(
            navigateToLogin = {
                navController.navigate(
                    route = Login,
                    navOptions = navController.clearBackStackNavOptions(),
                )
            },
            modifier = Modifier.padding(paddingValues),
        )
    }
}
