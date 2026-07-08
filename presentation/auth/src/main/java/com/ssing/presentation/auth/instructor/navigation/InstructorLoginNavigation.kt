package com.ssing.presentation.auth.instructor.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ssing.presentation.auth.instructor.InstructorLoginRoute
import kotlinx.serialization.Serializable

@Serializable
data object InstructorLogin

fun NavGraphBuilder.instructorAuthNavGraph(
    paddingValues: PaddingValues,
    navigateToHome: () -> Unit,
) {
    composable<InstructorLogin> {
        InstructorLoginRoute(
            navigateToHome = navigateToHome,
            modifier = Modifier
                .padding(paddingValues),
        )
    }
}