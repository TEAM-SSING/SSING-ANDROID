package com.ssing.consumer

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.ssing.presentation.auth.navigation.authNavGraph
import com.ssing.presentation.consumerhome.navigtion.ConsumerHome
import com.ssing.presentation.consumerhome.navigtion.consumerHomeNavGraph

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
        authNavGraph(paddingValues = paddingValues)
        consumerHomeNavGraph(
            paddingValues = paddingValues,
        )
    }
}