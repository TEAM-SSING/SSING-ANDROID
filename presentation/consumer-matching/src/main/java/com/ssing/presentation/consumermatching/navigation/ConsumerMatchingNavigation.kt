package com.ssing.presentation.consumermatching.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ssing.presentation.consumermatching.screen.ConsumerMatchingResultRoute
import kotlinx.serialization.Serializable

@Serializable
data object ConsumerMatchingResult

fun NavController.navigateToConsumerMatching() =
    this.navigate(ConsumerMatchingResult)

fun NavGraphBuilder.consumerMatchingNavGraph(
    paddingValues: PaddingValues,
    navController: NavController,
) {
    composable<ConsumerMatchingResult> {
        ConsumerMatchingResultRoute(
            popBackStack = navController::popBackStack,
            navigateToHome = {},
            navigateToPayment = {},
            modifier = Modifier.padding(paddingValues),
        )
    }
}
