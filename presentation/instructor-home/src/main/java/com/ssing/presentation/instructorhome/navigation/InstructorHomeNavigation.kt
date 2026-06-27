package com.ssing.presentation.instructorhome.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ssing.presentation.instructorhome.InstructorHomeRoute
import kotlinx.serialization.Serializable

@Serializable
data object InstructorHome

fun NavGraphBuilder.instructorHomeNavGraph(
    paddingValues: PaddingValues,
) {
    composable<InstructorHome> {
        InstructorHomeRoute(
            modifier = Modifier.padding(paddingValues),
        )
    }
}
