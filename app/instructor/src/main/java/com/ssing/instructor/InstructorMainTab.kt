package com.ssing.instructor

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.ssing.core.ui.navigation.MainTabRoute
import com.ssing.core.ui.navigation.Route
import com.ssing.instructor.R
import com.ssing.instructor.navigation.InstructorDummyTabRoute
import com.ssing.presentation.instructorhome.navigation.InstructorHome

enum class InstructorMainTab(
    @param:DrawableRes val selectedIconRes: Int,
    @param:DrawableRes val unselectedIconRes: Int,
    @param:StringRes val titleRes: Int,
    val route: MainTabRoute,
) {
    HOME(
        selectedIconRes = com.ssing.core.ui.R.drawable.ic_home_selected,
        unselectedIconRes = com.ssing.core.ui.R.drawable.ic_home_unselected,
        titleRes = R.string.instructor_home,
        route = InstructorHome,
    ),
    RESERVATION(
        selectedIconRes = com.ssing.core.ui.R.drawable.ic_reservation_selected,
        unselectedIconRes = com.ssing.core.ui.R.drawable.ic_reservation_unselected,
        titleRes = R.string.instructor_reservation,
        route = InstructorDummyTabRoute,
    ),
    CHAT(
        selectedIconRes = com.ssing.core.ui.R.drawable.ic_chat_selected,
        unselectedIconRes = com.ssing.core.ui.R.drawable.ic_chat_unselected,
        titleRes = R.string.instructor_chat,
        route = InstructorDummyTabRoute,
    ),
    SETTLEMENT(
        selectedIconRes = com.ssing.core.ui.R.drawable.ic_wallet_selected,
        unselectedIconRes = com.ssing.core.ui.R.drawable.ic_wallet_unselected,
        titleRes = R.string.instructor_settlement,
        route = InstructorDummyTabRoute,
    ),
    PROFILE(
        selectedIconRes = com.ssing.core.ui.R.drawable.ic_profile_selected,
        unselectedIconRes = com.ssing.core.ui.R.drawable.ic_profile_unselected,
        titleRes = R.string.instructor_profile,
        route = InstructorDummyTabRoute,
    );

    companion object {
        fun find(predicate: (MainTabRoute) -> Boolean): InstructorMainTab? {
            return values().find { predicate(it.route) }
        }

        fun contains(predicate: (Route) -> Boolean): Boolean {
            return values().any { predicate(it.route) }
        }
    }
}
