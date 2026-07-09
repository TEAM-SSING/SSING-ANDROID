package com.ssing.presentation.auth.instructor.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ssing.presentation.auth.instructor.InstructorLoginRoute
import kotlinx.serialization.Serializable

@Serializable
data object InstructorLogin

fun NavGraphBuilder.instructorAuthNavGraph(
    navigateToHome: () -> Unit,
) {
    composable<InstructorLogin> {
        InstructorLoginRoute(
            navigateToHome = navigateToHome,
        )
    }
}