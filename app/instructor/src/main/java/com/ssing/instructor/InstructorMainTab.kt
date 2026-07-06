package com.ssing.instructor

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.ssing.core.ui.R.drawable.ic_chat
import com.ssing.core.ui.R.drawable.ic_home
import com.ssing.core.ui.R.drawable.ic_profile
import com.ssing.core.ui.R.drawable.ic_reservation
import com.ssing.core.ui.R.drawable.ic_wallet
import com.ssing.core.ui.navigation.MainTab
import com.ssing.core.ui.navigation.MainTabRoute
import com.ssing.core.ui.navigation.Route
import com.ssing.instructor.navigation.InstructorDummyTabRoute
import com.ssing.presentation.instructorhome.navigation.InstructorHome

enum class InstructorMainTab(
    @param:DrawableRes override val selectedIconRes: Int,
    @param:DrawableRes override val unselectedIconRes: Int,
    @param:StringRes override val titleRes: Int,
    override val route: MainTabRoute,
) : MainTab {
    HOME(
        selectedIconRes = ic_home,
        unselectedIconRes = ic_home,
        titleRes = R.string.instructor_home,
        route = InstructorHome,
    ),
    RESERVATION(
        selectedIconRes = ic_reservation,
        unselectedIconRes = ic_reservation,
        titleRes = R.string.instructor_reservation,
        route = InstructorDummyTabRoute,
    ),
    CHAT(
        selectedIconRes = ic_chat,
        unselectedIconRes = ic_chat,
        titleRes = R.string.instructor_chat,
        route = InstructorDummyTabRoute,
    ),
    SETTLEMENT(
        selectedIconRes = ic_wallet,
        unselectedIconRes = ic_wallet,
        titleRes = R.string.instructor_settlement,
        route = InstructorDummyTabRoute,
    ),
    PROFILE(
        selectedIconRes = ic_profile,
        unselectedIconRes = ic_profile,
        titleRes = R.string.instructor_profile,
        route = InstructorDummyTabRoute,
    );

    companion object {
        fun find(predicate: (MainTabRoute) -> Boolean): InstructorMainTab? {
            return entries.find { predicate(it.route) }
        }

        fun contains(predicate: (Route) -> Boolean): Boolean {
            return entries.any { predicate(it.route) }
        }
    }
}
