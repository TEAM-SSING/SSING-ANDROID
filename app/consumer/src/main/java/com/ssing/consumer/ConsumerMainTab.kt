package com.ssing.consumer

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.ssing.consumer.navigation.ConsumerDummyTabRoute
import com.ssing.core.ui.R.drawable
import com.ssing.core.ui.navigation.MainTab
import com.ssing.core.ui.navigation.MainTabRoute
import com.ssing.core.ui.navigation.Route
import com.ssing.presentation.consumerhome.navigation.ConsumerHome

enum class ConsumerMainTab(
    @param:DrawableRes override val selectedIconRes: Int,
    @param:DrawableRes override val unselectedIconRes: Int,
    @param:StringRes override val titleRes: Int,
    override val route: MainTabRoute,
) : MainTab {
    HOME(
        selectedIconRes = drawable.ic_home_selected,
        unselectedIconRes = drawable.ic_home_unselected,
        titleRes = R.string.consumer_home,
        route = ConsumerHome,
    ),

    RESERVATION(
        selectedIconRes = drawable.ic_reservation_selected,
        unselectedIconRes = drawable.ic_reservation_unselected,
        titleRes = R.string.consumer_reservation,
        route = ConsumerDummyTabRoute,
    ),
    CHAT(
        selectedIconRes = drawable.ic_chat_selected,
        unselectedIconRes = drawable.ic_chat_unselected,
        titleRes = R.string.consumer_chat,
        route = ConsumerDummyTabRoute,
    ),
    PROFILE(
        selectedIconRes = drawable.ic_profile_selected,
        unselectedIconRes = drawable.ic_profile_unselected,
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
