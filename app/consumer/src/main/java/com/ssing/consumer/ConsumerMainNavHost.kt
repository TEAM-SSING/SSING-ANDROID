package com.ssing.consumer

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.ssing.core.ui.extension.clearBackStackNavOptions
import com.ssing.core.ui.navigation.SsingNavHost
import com.ssing.presentation.auth.consumer.navigation.ConsumerLogin
import com.ssing.presentation.auth.consumer.navigation.consumerAuthNavGraph
import com.ssing.presentation.consumerhome.navigation.ConsumerHome
import com.ssing.presentation.consumerhome.navigation.consumerHomeNavGraph
import com.ssing.presentation.consumerlesson.navigation.ConsumerLesson
import com.ssing.presentation.consumerpayment.navigation.consumerPaymentNavGraph
import com.ssing.presentation.consumerlesson.navigation.consumerLessonNavGraph
import com.ssing.presentation.consumermatching.navigation.ConsumerMatchingCondition
import com.ssing.presentation.consumermatching.navigation.consumerMatchingNavGraph
import com.ssing.presentation.consumerprofile.navigation.consumerProfileNavGraph
import com.ssing.presentation.notification.navigation.notificationNavGraph

@Composable
internal fun ConsumerMainNavHost(
    navController: NavHostController,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
) {
    SsingNavHost(
        navController = navController,
        startDestination = ConsumerHome,
        modifier = modifier.fillMaxSize(),
    ) {
        consumerHomeNavGraph(
            paddingValues = paddingValues,
            navigateToLessonDetail = {
                navController.navigate(
                    route = ConsumerLesson,
                )
            },
            navigateToMatching = {
                navController.navigate(
                    route = ConsumerMatchingCondition,
                )
            },
        )
        consumerProfileNavGraph(
            paddingValues = paddingValues,
            navigateToLogin = {
                navController.navigate(
                    route = ConsumerLogin,
                    navOptions = navController.clearBackStackNavOptions(),
                )
            },
        )
        consumerPaymentNavGraph(
            navigateToLesson = {
                navController.navigate(
                    route = ConsumerLesson,
                    navOptions = navController.clearBackStackNavOptions(),
                )
            },
            navigateToHome =  {
                navController.navigate(
                    route = ConsumerHome,
                    navOptions = navController.clearBackStackNavOptions(),
                )
            }
        )
        notificationNavGraph(
            paddingValues = paddingValues,
            navController = navController,
        )
        consumerMatchingNavGraph(
            navController = navController,
            paddingValues = paddingValues,
            navigateToHome = {
                navController.navigate(
                    route = ConsumerHome,
                    navOptions = navController.clearBackStackNavOptions(),
                )
            },
        )
        consumerAuthNavGraph(
            paddingValues = paddingValues,
            navigateToHome = {
                navController.navigate(
                    route = ConsumerHome,
                    navOptions = navController.clearBackStackNavOptions(),
                )
            },
        )
        consumerLessonNavGraph(
            paddingValues = paddingValues,
            navigateToHome = {
                navController.navigate(
                    route = ConsumerHome,
                    navOptions = navController.clearBackStackNavOptions(),
                )
            },
        )
    }
}
