package com.ssing.presentation.consumerlesson.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ssing.core.ui.navigation.Route
import com.ssing.presentation.consumerlesson.ConsumerLessonRoute
import kotlinx.serialization.Serializable

@Serializable
data class ConsumerLesson(val lessonId: Long) : Route


fun NavGraphBuilder.consumerLessonNavGraph(
    navigateToHome: () -> Unit,
) {
    composable<ConsumerLesson> {
        ConsumerLessonRoute(
            navigateToHome = navigateToHome,
        )
    }
}
