package com.ssing.presentation.consumerpayment.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.ssing.core.ui.extension.slideComposable
import com.ssing.core.ui.navigation.Route
import com.ssing.presentation.consumerpayment.PaymentRoute
import kotlinx.serialization.Serializable

@Serializable
data object ConsumerPayment : Route

fun NavController.navigateToConsumerPayment() =
    navigate(ConsumerPayment)

fun NavGraphBuilder.consumerPaymentNavGraph(
    navigateToLesson: (Long) -> Unit,
    navigateToHome: () -> Unit,
) {
    slideComposable<ConsumerPayment> {
        PaymentRoute(
            navigateToLesson = navigateToLesson,
            navigateToHome = navigateToHome,
        )
    }
}
