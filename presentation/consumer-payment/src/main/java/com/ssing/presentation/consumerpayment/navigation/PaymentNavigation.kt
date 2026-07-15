package com.ssing.presentation.consumerpayment.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.ssing.core.ui.extension.slideComposable
import com.ssing.core.ui.navigation.Route
import com.ssing.presentation.consumerpayment.PaymentRoute
import kotlinx.serialization.Serializable

@Serializable
data class ConsumerPayment(val matchingRequestId: Long) : Route

fun NavController.navigateToConsumerPayment(matchingRequestId: Long) =
    navigate(ConsumerPayment(matchingRequestId))

fun NavGraphBuilder.consumerPaymentNavGraph(
    navigateToLesson: () -> Unit,
    navigateToHome: () -> Unit,
) {
    slideComposable<ConsumerPayment> {
        PaymentRoute(
            navigateToLesson = navigateToLesson,
            navigateToHome = navigateToHome,
        )
    }
}
