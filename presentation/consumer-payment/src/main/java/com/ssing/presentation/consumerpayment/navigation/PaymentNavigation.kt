package com.ssing.presentation.consumerpayment.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ssing.core.ui.navigation.Route
import com.ssing.presentation.consumerpayment.PaymentRoute
import kotlinx.serialization.Serializable

@Serializable
data object ConsumerPayment : Route

fun NavGraphBuilder.consumerPaymentNavGraph(
    navigateToLesson: () -> Unit,
    navigateToHome: () -> Unit,
) {
    composable<ConsumerPayment> {
        PaymentRoute(
            navigateToLesson = navigateToLesson,
            navigateToHome = navigateToHome,
        )
    }
}
