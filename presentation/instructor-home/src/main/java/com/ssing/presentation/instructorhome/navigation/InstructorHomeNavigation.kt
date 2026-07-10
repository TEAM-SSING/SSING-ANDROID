package com.ssing.presentation.instructorhome.navigation

import InstructorHomeRoute
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ssing.core.ui.navigation.MainTabRoute
import kotlinx.serialization.Serializable

@Serializable
data object InstructorHome : MainTabRoute

fun NavGraphBuilder.instructorHomeNavGraph(
    paddingValues: PaddingValues,
) {
    composable<InstructorHome> {
        InstructorHomeRoute(
            navigateToMatching = {},
            navigateToLessonDetail = {},
            modifier = Modifier.padding(paddingValues),
        )
    }
}
