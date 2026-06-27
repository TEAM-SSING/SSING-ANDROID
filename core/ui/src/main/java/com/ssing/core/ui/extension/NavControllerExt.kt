package com.ssing.core.ui.extension

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.navOptions

/**
 * 백스택을 완전히 초기화하고 루트(0)로 이동하는 NavOptions를 생성합니다.
 *
 * 상태 저장 및 복원을 수행하지 않으므로, 화면 이동 시 이전 상태가 초기화됩니다.
 *
 * 사용 사례:
 * - 로그아웃 후 로그인 화면으로 이동 (이전 사용자 상태 초기화 필요)
 * - 특정 플로우 완료 후 완전히 새로운 화면으로 시작
 *
 * @return 백스택 초기화 설정만 포함된 NavOptions
 */
fun NavController.clearBackStackNavOptions() =
    navOptions {
        popUpTo(graph.findStartDestination().id) {
            inclusive = true
        }
        launchSingleTop = true
    }

/**
 * 백스택을 초기화하면서도 이전 Destination의 상태를 저장하고 복원하는 NavOptions를 생성합니다.
 *
 * 사용 사례:
 * - 탭 전환 시 백스택 클리어 + 이전 탭 상태 복원 (Bottom Navigation)
 * - 특정 화면에서 메인으로 복귀하되 상태 유지 필요 시
 *
 * @return 백스택 초기화 및 상태 저장/복원 설정이 포함된 NavOptions
 */
fun NavController.clearBackStackWithRestoreNavOptions() =
    navOptions {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
