package com.ssing.presentation.instructorprofile.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ssing.core.ui.navigation.MainTabRoute
import com.ssing.presentation.instructorprofile.InstructorProfileRoute
import kotlinx.serialization.Serializable

@Serializable
data object InstructorProfile : MainTabRoute

fun NavGraphBuilder.instructorProfileNavGraph(
    paddingValues: PaddingValues,
    navigateToLogin: () -> Unit,
) {
    composable<InstructorProfile> {
        InstructorProfileRoute(
            navigateToLogin,
            Modifier.padding(paddingValues),
        )
    }
}
