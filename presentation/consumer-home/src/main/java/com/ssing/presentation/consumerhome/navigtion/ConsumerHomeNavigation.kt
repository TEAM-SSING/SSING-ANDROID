package com.ssing.presentation.consumerhome.navigtion

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ssing.core.ui.navigation.MainTabRoute
import com.ssing.presentation.consumerhome.ConsumerHomeRoute
import kotlinx.serialization.Serializable

@Serializable
data object ConsumerHome : MainTabRoute

fun NavGraphBuilder.consumerHomeNavGraph(
    paddingValues: PaddingValues,
) {
    composable<ConsumerHome> {
        ConsumerHomeRoute(
            modifier = Modifier.padding(paddingValues),
        )
    }
}
