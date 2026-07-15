package com.ssing.presentation.devauth.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ssing.core.ui.navigation.Route
import com.ssing.presentation.devauth.DevAuthRoute
import kotlinx.serialization.Serializable

@Serializable
data object DevAuth : Route

fun NavGraphBuilder.devAuthNavGraph(
    paddingValues: PaddingValues,
    navigateToHome: () -> Unit,
) {
    composable<DevAuth> {
        DevAuthRoute(
            navigateToHome = navigateToHome,
            modifier = Modifier.padding(paddingValues),
        )
    }
}
