package com.ssing.presentation.consumerlesson.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ssing.core.ui.navigation.Route
import com.ssing.presentation.consumerlesson.ConsumerLessonRoute
import kotlinx.serialization.Serializable

@Serializable
data object ConsumerLesson : Route


fun NavGraphBuilder.consumerLessonNavGraph(
    paddingValues: PaddingValues,
    navigateToHome: () -> Unit,
) {
    composable<ConsumerLesson> {
        ConsumerLessonRoute(
            navigateToHome = navigateToHome,
        )
    }
}
