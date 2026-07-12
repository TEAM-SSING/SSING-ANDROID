package com.ssing.presentation.consumerprofile.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ssing.core.ui.navigation.MainTabRoute
import com.ssing.presentation.consumerprofile.ConsumerProfileRoute
import kotlinx.serialization.Serializable

@Serializable
data object ConsumerProfile : MainTabRoute

fun NavGraphBuilder.consumerProfileNavGraph(
    paddingValues: PaddingValues,
    navigateToLogin: () -> Unit,
) {
    composable<ConsumerProfile> {
        ConsumerProfileRoute(
            navigateToLogin = navigateToLogin,
            modifier = Modifier.padding(paddingValues),
        )
    }
}
