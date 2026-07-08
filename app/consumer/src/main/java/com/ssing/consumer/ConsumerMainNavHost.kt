package com.ssing.consumer

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.ssing.core.ui.extension.clearBackStackNavOptions
import com.ssing.presentation.auth.navigation.Login
import com.ssing.presentation.auth.navigation.authNavGraph
import com.ssing.presentation.consumerhome.navigation.ConsumerHome
import com.ssing.presentation.consumerhome.navigation.consumerHomeNavGraph
import com.ssing.presentation.notification.navigation.notificationNavGraph

@Composable
internal fun ConsumerMainNavHost(
    navController: NavHostController,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = ConsumerHome,
        modifier = modifier.fillMaxSize(),
    ) {
        authNavGraph(
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
        )
        notificationNavGraph(
            paddingValues = paddingValues,
            navController = navController,
            // TODO: 대상 화면(강습 도착 / 매칭 로딩중 / 강습전) 구현 후 실제 라우트로 연결
            navigateToLessonArrival = { },
            navigateToMatchingLoading = { },
            navigateToLessonReady = { },
        )
    }
}
