package com.ssing.presentation.consumermatching.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ssing.presentation.consumermatching.condition.ConsumerMatchingConditionRoute
import com.ssing.core.ui.navigation.Route
import kotlinx.serialization.Serializable

@Serializable
data object ConsumerMatchingCondition : Route

fun NavController.navigateToConsumerMatchingCondition() =
    navigate(ConsumerMatchingCondition)

fun NavGraphBuilder.consumerMatchingNavGraph(
    navController: NavController,
    paddingValues: PaddingValues,
) {
    composable<ConsumerMatchingCondition> {
        ConsumerMatchingConditionRoute(
            onPopBackStack = navController::popBackStack,
            navigateToMatching = {},
            modifier = Modifier.padding(paddingValues),
        )
    }
}
