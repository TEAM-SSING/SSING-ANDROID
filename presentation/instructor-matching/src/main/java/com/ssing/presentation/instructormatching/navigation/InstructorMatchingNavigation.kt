package com.ssing.presentation.instructormatching.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ssing.core.ui.navigation.Route
import com.ssing.presentation.instructormatching.screen.MatchingRoute
import kotlinx.serialization.Serializable

@Serializable
data object InstructorMatching: Route

fun NavGraphBuilder.instructorMatchingNavGraph(
    paddingValues: PaddingValues,
    navController: NavController,
) {
    composable<InstructorMatching> {
        MatchingRoute(
            navigateBack = { navController.popBackStack() },
            modifier = Modifier.padding(paddingValues),
        )
    }
}
