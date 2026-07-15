package com.ssing.presentation.instructorhome.navigation

import androidx.compose.foundation.layout.PaddingValues
import com.ssing.presentation.instructorhome.InstructorHomeRoute
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ssing.core.ui.navigation.MainTabRoute
import kotlinx.serialization.Serializable

@Serializable
data object InstructorHome : MainTabRoute

fun NavGraphBuilder.instructorHomeNavGraph(
    paddingValues: PaddingValues,
    navigateToMatching: () -> Unit,
    navigateToNotification: () -> Unit,
    navigateToLessonDetail: (Long?) -> Unit,
    navigateToMatchingWaiting: () -> Unit,
) {
    composable<InstructorHome> {
        InstructorHomeRoute(
            contentPadding = paddingValues,
            navigateToMatching = navigateToMatching,
            navigateToLessonDetail = navigateToLessonDetail,
            navigateToMatchingWaiting = navigateToMatchingWaiting,
            navigateToNotification = navigateToNotification,
        )
    }
}
