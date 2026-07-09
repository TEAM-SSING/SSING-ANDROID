package com.ssing.presentation.instructorprofile.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ssing.core.ui.navigation.MainTabRoute
import com.ssing.presentation.instructorprofile.ProfileRoute
import kotlinx.serialization.Serializable

@Serializable
data object Profile : MainTabRoute

fun NavGraphBuilder.profileNavGraph(
    paddingValues: PaddingValues,
    navigateToLogin: () -> Unit,
) {
    composable<Profile> {
        ProfileRoute(
            navigateToLogin = navigateToLogin,
            modifier = Modifier.padding(paddingValues),
        )
    }
}
