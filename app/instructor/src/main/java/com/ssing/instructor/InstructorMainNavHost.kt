package com.ssing.instructor

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.ssing.core.ui.extension.clearBackStackNavOptions
import com.ssing.core.ui.navigation.SsingNavHost
import com.ssing.presentation.auth.instructor.navigation.instructorAuthNavGraph
import com.ssing.presentation.instructorhome.navigation.InstructorHome
import com.ssing.presentation.instructorhome.navigation.instructorHomeNavGraph
import com.ssing.presentation.instructorlessondetail.navigation.InstructorLesson
import com.ssing.presentation.instructormatching.navigation.InstructorMatching
import com.ssing.presentation.notification.navigation.notificationNavGraph
import com.ssing.presentation.instructormatching.navigation.instructorMatchingNavGraph
import com.ssing.presentation.instructorlessondetail.navigation.instructorLessonNavGraph
import com.ssing.presentation.auth.instructor.navigation.InstructorLogin
import com.ssing.presentation.instructorprofile.navigation.instructorProfileNavGraph

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
        instructorAuthNavGraph(
            navigateToHome = {
                navController.navigate(
                    route = InstructorHome,
                    navOptions = navController.clearBackStackNavOptions(),
                )
            },
        )
        instructorHomeNavGraph(
            paddingValues = paddingValues,
            navigateToMatching = {
                navController.navigate(
                    route = InstructorMatching,
                )
            },
            navigateToLessonDetail = { lessonId ->
                navController.navigate(
                    route = InstructorLesson(lessonId = lessonId),
                )
            }
        )
        notificationNavGraph(
            paddingValues = paddingValues,
            navController = navController,
        )
        instructorMatchingNavGraph(
            paddingValues = paddingValues,
            navController = navController,
        )
        instructorLessonNavGraph(
            paddingValues = paddingValues,
            navController = navController,
        )
        instructorProfileNavGraph(
            paddingValues = paddingValues,
            navigateToLogin = {
                navController.navigate(
                    route = InstructorLogin,
                    navOptions = navController.clearBackStackNavOptions(),
                )
            },
        )
    }
}
