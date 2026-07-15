package com.ssing.consumer

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.ssing.core.ui.extension.clearBackStackNavOptions
import com.ssing.core.ui.navigation.Route
import com.ssing.core.ui.navigation.SsingNavHost
import com.ssing.presentation.auth.consumer.navigation.ConsumerLogin
import com.ssing.presentation.auth.consumer.navigation.consumerAuthNavGraph
import com.ssing.presentation.consumerhome.navigation.ConsumerHome
import com.ssing.presentation.consumerhome.navigation.consumerHomeNavGraph
import com.ssing.presentation.consumerlesson.navigation.ConsumerLesson
import com.ssing.presentation.consumerlesson.navigation.consumerLessonNavGraph
import com.ssing.presentation.consumermatching.navigation.consumerMatchingNavGraph
import com.ssing.presentation.consumermatching.navigation.navigateToConsumerMatching
import com.ssing.presentation.consumermatching.navigation.navigateToConsumerMatchingCondition
import com.ssing.presentation.consumerpayment.navigation.consumerPaymentNavGraph
import com.ssing.presentation.consumerpayment.navigation.navigateToConsumerPayment
import com.ssing.presentation.consumerprofile.navigation.consumerProfileNavGraph
import com.ssing.presentation.devauth.navigation.devAuthNavGraph
import com.ssing.presentation.notification.navigation.notificationNavGraph

@Composable
internal fun ConsumerMainNavHost(
    startDestination: Route,
    navController: NavHostController,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
) {
    SsingNavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier.fillMaxSize(),
    ) {
        devAuthNavGraph(
            paddingValues = paddingValues,
            navigateToHome = {
                navController.navigate(
                    route = ConsumerHome,
                    navOptions = navController.clearBackStackNavOptions(),
                )
            },
        )
        consumerHomeNavGraph(
            paddingValues = paddingValues,
            navigateToLessonDetail = {
                navController.navigate(
                    route = ConsumerLesson,
                )
            },
            navigateToMatching = { navController.navigateToConsumerMatchingCondition() },
            navigateToActiveMatching = { matchingRequestId ->
                navController.navigateToConsumerMatching(matchingRequestId, isRecoveredEntry = true)
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
            navigateToPayment = { navController.navigateToConsumerPayment() },
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
            navigateToHome = {
                navController.navigate(
                    route = ConsumerHome,
                    navOptions = navController.clearBackStackNavOptions(),
                )
            },
        )
    }
}
