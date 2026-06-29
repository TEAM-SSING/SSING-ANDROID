package com.ssing.presentation.auth.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ssing.presentation.auth.LoginRoute
import kotlinx.serialization.Serializable

@Serializable
data object Login

fun NavGraphBuilder.authNavGraph(
    paddingValues: PaddingValues,
    navigateToHome: () -> Unit,
) {
    composable<Login> {
        LoginRoute(
            navigateToHome = navigateToHome,
            modifier = Modifier.padding(paddingValues),
        )
    }
}
