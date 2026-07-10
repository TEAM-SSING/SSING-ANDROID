package com.ssing.presentation.instructorlessondetail.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ssing.core.ui.navigation.Route
import com.ssing.presentation.instructorlessondetail.screen.LessonDetailRoute
import kotlinx.serialization.Serializable

@Serializable
data object InstructorLesson: Route

fun NavGraphBuilder.instructorLessonNavGraph(
    paddingValues: PaddingValues,
    navController: NavController,
) {
    composable<InstructorLesson> {
        LessonDetailRoute(
            navigateBack = navController::popBackStack,
            modifier = Modifier.padding(paddingValues),
        )
    }
}