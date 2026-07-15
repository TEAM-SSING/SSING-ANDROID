package com.ssing.presentation.consumerlesson.navigation

import androidx.navigation.NavGraphBuilder
import com.ssing.core.ui.extension.slideComposable
import com.ssing.core.ui.navigation.Route
import com.ssing.presentation.consumerlesson.ConsumerLessonRoute
import kotlinx.serialization.Serializable

@Serializable
data class ConsumerLesson(val lessonId: Long) : Route


fun NavGraphBuilder.consumerLessonNavGraph(
    navigateToHome: () -> Unit,
) {
    slideComposable<ConsumerLesson> {
        ConsumerLessonRoute(
            navigateToHome = navigateToHome,
        )
    }
}
