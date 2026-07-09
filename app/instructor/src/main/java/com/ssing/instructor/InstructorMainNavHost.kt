package com.ssing.instructor

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.ssing.core.ui.extension.clearBackStackNavOptions
import com.ssing.presentation.auth.navigation.Login
import com.ssing.presentation.auth.navigation.authNavGraph
import com.ssing.presentation.instructorhome.navigation.InstructorHome
import com.ssing.presentation.instructorhome.navigation.instructorHomeNavGraph
import com.ssing.presentation.instructormatching.navigation.instructorMatchingNavGraph
import com.ssing.presentation.instructorprofile.navigation.profileNavGraph

@Composable
internal fun InstructorMainNavHost(
    navController: NavHostController,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
) {
    NavHost(
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
        profileNavGraph(
            paddingValues = paddingValues,
            navigateToLogin = {
                navController.navigate(
                    route = Login,
                    navOptions = navController.clearBackStackNavOptions(),
                )
            },
        )
    }
}
