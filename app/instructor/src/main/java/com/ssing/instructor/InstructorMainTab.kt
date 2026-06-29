package com.ssing.instructor

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.ssing.instructor.navigation.InstructorDummyTabRoute
import com.ssing.core.ui.navigation.MainTabRoute
import com.ssing.core.ui.navigation.Route
import com.ssing.presentation.instructorhome.navigation.InstructorHome

enum class InstructorMainTab(
    @param:DrawableRes val iconRes: Int,
    @param:StringRes val titleRes: Int,
    val route: MainTabRoute,
) {
    HOME(
        iconRes = R.drawable.ic_launcher_background,
        titleRes = R.string.instructor_home,
        route = InstructorHome,
    ),

    // TODO: 추후 변경 예정 앱잼 MVP 아님 비활성화 상태
    RESERVATION(
        iconRes = R.drawable.ic_launcher_background,
        titleRes = R.string.instructor_reservation,
        route = InstructorDummyTabRoute,
    ),
    CHAT(
        iconRes = R.drawable.ic_launcher_background,
        titleRes = R.string.instructor_chat,
        route = InstructorDummyTabRoute,
    ),
    SETTLEMENT(
        iconRes = R.drawable.ic_launcher_background,
        titleRes = R.string.instructor_settlement,
        route = InstructorDummyTabRoute,
    ),
    PROFILE(
        iconRes = R.drawable.ic_launcher_background,
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