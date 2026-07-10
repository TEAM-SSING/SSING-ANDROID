package com.ssing.presentation.auth.consumer.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ssing.core.ui.navigation.Route
import com.ssing.presentation.auth.consumer.ConsumerLoginRoute
import kotlinx.serialization.Serializable

@Serializable
data object ConsumerLogin : Route

fun NavGraphBuilder.consumerAuthNavGraph(
    paddingValues: PaddingValues,
    navigateToHome: () -> Unit,
) {
    composable<ConsumerLogin> {
        ConsumerLoginRoute(
            navigateToHome = navigateToHome,
            modifier = Modifier.padding(paddingValues),
        )
    }
}