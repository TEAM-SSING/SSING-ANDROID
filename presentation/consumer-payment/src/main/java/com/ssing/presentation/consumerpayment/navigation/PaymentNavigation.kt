package com.ssing.presentation.consumerpayment.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ssing.core.ui.navigation.MainTabRoute
import com.ssing.presentation.consumerpayment.PaymentRoute
import kotlinx.serialization.Serializable

@Serializable
data object ConsumerPayment : MainTabRoute

fun NavGraphBuilder.consumerPaymentNavGraph(
    paddingValues: PaddingValues,
    navController: NavController,
) {
    composable<ConsumerPayment> {
        PaymentRoute(
            popBackStack = navController::popBackStack,
            navigateToLesson = {},
            modifier = Modifier.padding(paddingValues),
        )
    }
}
