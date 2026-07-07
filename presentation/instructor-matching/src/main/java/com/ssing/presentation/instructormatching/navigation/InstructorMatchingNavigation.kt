package com.ssing.presentation.instructormatching.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ssing.presentation.instructormatching.screen.MatchingRoute
import kotlinx.serialization.Serializable

@Serializable
data object InstructorMatching

fun NavGraphBuilder.instructorMatchingNavGraph(
    paddingValues: PaddingValues,
    navigateBack: () -> Unit,
) {
    composable<InstructorMatching> {
        MatchingRoute(
            navigateBack = navigateBack,
            modifier = Modifier.padding(paddingValues),
        )
    }
}
