package com.ssing.presentation.consumermatching.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.ssing.core.ui.extension.sharedViewModel
import com.ssing.core.ui.extension.slideComposable
import com.ssing.core.ui.navigation.Route
import com.ssing.presentation.consumermatching.condition.ConsumerMatchingConditionRoute
import com.ssing.presentation.consumermatching.screen.ConsumerMatchingFailureRoute
import com.ssing.presentation.consumermatching.screen.ConsumerMatchingPendingRoute
import com.ssing.presentation.consumermatching.screen.ConsumerMatchingResultRoute
import kotlinx.serialization.Serializable

@Serializable
data object ConsumerMatchingCondition : Route

@Serializable
private data object ConsumerMatchingGraph : Route

@Serializable
data object ConsumerMatchingPending : Route

@Serializable
data object ConsumerMatchingResult : Route

@Serializable
data object ConsumerMatchingFailure : Route

private fun NavController.navigateToConsumerMatchingPending() =
    this.navigate(ConsumerMatchingGraph)

private fun NavController.navigateToConsumerMatchingResult() =
    this.navigate(ConsumerMatchingResult)

private fun NavController.navigateToConsumerMatchingFailure() =
    this.navigate(ConsumerMatchingFailure)

fun NavGraphBuilder.consumerMatchingNavGraph(
    navigateToHome: () -> Unit,
    navController: NavHostController,
    paddingValues: PaddingValues,
) {
    slideComposable<ConsumerMatchingCondition> {
        ConsumerMatchingConditionRoute(
            onPopBackStack = navController::popBackStack,
            navigateToMatchingPending = navController::navigateToConsumerMatchingPending,
        )
    }

    navigation<ConsumerMatchingGraph>(startDestination = ConsumerMatchingPending) {
        composable<ConsumerMatchingPending> { backStackEntry ->
            ConsumerMatchingPendingRoute(
                popBackStack = navController::popBackStack,
                navigateToResult = navController::navigateToConsumerMatchingResult,
                navigateToFailure = navController::navigateToConsumerMatchingFailure,
                modifier = Modifier.padding(paddingValues),
                viewModel = sharedViewModel(backStackEntry, navController),
            )
        }
        composable<ConsumerMatchingResult> { backStackEntry ->
            ConsumerMatchingResultRoute(
                popBackStack = navController::popBackStack,
                navigateToHome = navigateToHome,
                navigateToPayment = {},
                modifier = Modifier.padding(paddingValues),
                viewModel = sharedViewModel(backStackEntry, navController),
            )
        }
        composable<ConsumerMatchingFailure> { backStackEntry ->
            ConsumerMatchingFailureRoute(
                navigateToHome = navigateToHome,
                modifier = Modifier.padding(paddingValues),
                viewModel = sharedViewModel(backStackEntry, navController),
            )
        }
    }
}
