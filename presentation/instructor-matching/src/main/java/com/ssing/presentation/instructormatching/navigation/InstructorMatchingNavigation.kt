package com.ssing.presentation.instructormatching.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.ssing.core.ui.navigation.Route
import com.ssing.presentation.instructormatching.screen.MatchingRoute
import kotlinx.serialization.Serializable

/**
 * 매칭 화면 Route.
 *
 * @param offerId FCM '새 강습 도착' 딥링크로 진입 시 특정 제안 id. 그 외(거절/일반 진입)엔 null.
 */
@Serializable
data class InstructorMatching(val offerId: Long? = null, val startFresh: Boolean = false) : Route

fun NavGraphBuilder.instructorMatchingNavGraph(
    paddingValues: PaddingValues,
    navController: NavController,
    navigateToLessonDetail: (Long) -> Unit,
) {
    composable<InstructorMatching>(
        deepLinks = listOf(
            navDeepLink<InstructorMatching>(basePath = "https://ssing.app/instructor-matching"),
        ),
    ) {
        MatchingRoute(
            navigateBack = { navController.popBackStack() },
            navigateToLessonDetail = navigateToLessonDetail,
            modifier = Modifier.padding(paddingValues),
        )
    }
}
