package com.ssing.consumer

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.ssing.consumer.navigation.ConsumerDummyTabRoute
import com.ssing.core.ui.navigation.MainTabRoute
import com.ssing.core.ui.navigation.Route
import com.ssing.presentation.consumerhome.navigtion.ConsumerHome

enum class ConsumerMainTab(
    @param:DrawableRes val iconRes: Int,
    @param:StringRes val titleRes: Int,
    val route: MainTabRoute,
) {
    HOME(
        iconRes = R.drawable.ic_launcher_background,
        titleRes = R.string.consumer_home,
        route = ConsumerHome,
    ),

    // TODO: 추후 변경 예정 앱잼 MVP 아님 비활성화 상태
    RESERVATION(
        iconRes = R.drawable.ic_launcher_background,
        titleRes = R.string.consumer_reservation,
        route = ConsumerDummyTabRoute,
    ),
    LESSON(
        iconRes = R.drawable.ic_launcher_background,
        titleRes = R.string.consumer_lesson,
        route = ConsumerDummyTabRoute,
    ),
    PROFILE(
        iconRes = R.drawable.ic_launcher_background,
        titleRes = R.string.consumer_profile,
        route = ConsumerDummyTabRoute,
    );

    companion object {
        fun find(predicate: (MainTabRoute) -> Boolean): ConsumerMainTab? {
            return entries.find { predicate(it.route) }
        }

        fun contains(predicate: (Route) -> Boolean): Boolean {
            return entries.any { predicate(it.route) }
        }
    }
}
