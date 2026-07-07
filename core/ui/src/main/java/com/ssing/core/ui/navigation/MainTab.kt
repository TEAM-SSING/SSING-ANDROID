package com.ssing.core.ui.navigation

interface MainTab {
    val name: String
    val selectedIconRes: Int
    val unselectedIconRes: Int
    val titleRes: Int
    val route: MainTabRoute
}
