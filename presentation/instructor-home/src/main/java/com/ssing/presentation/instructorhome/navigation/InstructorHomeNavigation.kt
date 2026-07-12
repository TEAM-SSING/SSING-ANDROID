package com.ssing.presentation.instructorhome.navigation

import com.ssing.presentation.instructorhome.InstructorHomeRoute
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ssing.core.ui.navigation.MainTabRoute
import kotlinx.serialization.Serializable

@Serializable
data object InstructorHome : MainTabRoute

fun NavGraphBuilder.instructorHomeNavGraph(
    navigateToMatching: () -> Unit,
    navigateToLessonDetail: (Long) -> Unit,
) {
    composable<InstructorHome> {
        InstructorHomeRoute(
            navigateToMatching = navigateToMatching,
            navigateToLessonDetail = navigateToLessonDetail,
        )
    }
}
