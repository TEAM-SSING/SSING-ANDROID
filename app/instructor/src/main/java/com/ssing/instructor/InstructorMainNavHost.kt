package com.ssing.instructor

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.ssing.core.ui.extension.clearBackStackNavOptions
import com.ssing.core.ui.navigation.SsingNavHost
import com.ssing.presentation.auth.navigation.authNavGraph
import com.ssing.presentation.instructorhome.navigation.InstructorHome
import com.ssing.presentation.instructorhome.navigation.instructorHomeNavGraph
import com.ssing.presentation.instructormatching.navigation.instructorMatchingNavGraph

@Composable
internal fun InstructorMainNavHost(
    navController: NavHostController,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
) {
    SsingNavHost(
        navController = navController,
        startDestination = InstructorHome,
        modifier = modifier.fillMaxSize(),
    ) {
        authNavGraph(
            paddingValues = paddingValues,
            navigateToHome = {
                navController.navigate(
                    route = InstructorHome,
                    navOptions = navController.clearBackStackNavOptions(),
                )
            },
        )
        instructorHomeNavGraph(paddingValues = paddingValues)
        instructorMatchingNavGraph(
            paddingValues = paddingValues,
            navController = navController,
        )
    }
}
