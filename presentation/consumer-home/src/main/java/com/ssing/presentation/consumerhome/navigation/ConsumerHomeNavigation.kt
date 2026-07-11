package com.ssing.presentation.consumerhome.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ssing.core.ui.navigation.MainTabRoute
import com.ssing.presentation.consumerhome.ConsumerHomeRoute
import kotlinx.serialization.Serializable

@Serializable
data object ConsumerHome : MainTabRoute

fun NavGraphBuilder.consumerHomeNavGraph(
    navigateToLessonDetail: (Long) -> Unit,
    navigateToMatching: () -> Unit,
) {
    composable<ConsumerHome> {
        ConsumerHomeRoute(
            navigateToLessonDetail = navigateToLessonDetail,
            navigateToMatching = navigateToMatching,
        )
    }
}
