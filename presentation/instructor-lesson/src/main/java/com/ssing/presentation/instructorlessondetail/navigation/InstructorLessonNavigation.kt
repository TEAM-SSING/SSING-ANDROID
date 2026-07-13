package com.ssing.presentation.instructorlessondetail.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ssing.core.ui.navigation.Route
import com.ssing.presentation.instructorlessondetail.screen.LessonDetailRoute
import kotlinx.serialization.Serializable

@Serializable
data class InstructorLesson(val lessonId :Long): Route

fun NavGraphBuilder.instructorLessonNavGraph(
    paddingValues: PaddingValues,
    navController: NavController,
) {
    composable<InstructorLesson> {
        LessonDetailRoute(
            navigateBack = navController::popBackStack,
        )
    }
}