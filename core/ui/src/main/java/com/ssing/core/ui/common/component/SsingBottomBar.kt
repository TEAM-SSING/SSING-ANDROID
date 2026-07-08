package com.ssing.core.ui.common.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.core.ui.navigation.MainTab
import kotlinx.collections.immutable.ImmutableList

/**
 * 앱 공통 하단 내비게이션 바.
 *
 * @param T [MainTab]을 구현하는 탭 enum 타입 (ConsumerMainTab / InstructorMainTab)
 * @param isVisible 바 표시 여부 (애니메이션 포함)
 * @param tabs 표시할 탭 목록
 * @param currentTab 현재 선택된 탭. null이면 아무 탭도 선택되지 않은 상태.
 * @param onTabSelected 탭 선택 콜백
 */
@Composable
fun <T : MainTab> SsingBottomBar(
    isVisible: Boolean,
    tabs: ImmutableList<T>,
    currentTab: T?,
    onTabSelected: (T) -> Unit,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn() + slideIn { IntOffset(0, it.height) },
        exit = fadeOut() + slideOut { IntOffset(0, it.height) },
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(
                    color = SSINGTheme.colors.backgroundNormal,
                    shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
                )
                .padding(horizontal = 16.dp)
                .navigationBarsPadding(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            tabs.forEach { tab ->
                key(tab.name) {
                    SsingBottomBarItem(
                        tab = tab,
                        isSelected = tab == currentTab,
                        onClick = { onTabSelected(tab) },
                        showNewMessageBadge = tab.name == "CHAT",
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
    }
}

@Composable
private fun SsingBottomBarItem(
    tab: MainTab,
    isSelected: Boolean,
    onClick: () -> Unit,
    showNewMessageBadge: Boolean,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val (textColor, iconRes) = when {
        isSelected -> Pair(SSINGTheme.colors.textStrong, tab.selectedIconRes)
        else -> Pair(SSINGTheme.colors.textAlternative, tab.unselectedIconRes)
    }

    Column(
        modifier = modifier
            .background(
                color = if (isPressed) SSINGTheme.colors.borderDisabled else Color.Transparent,
                shape = RoundedCornerShape(12.dp),
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            )
            .padding(top = 8.dp, bottom = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(iconRes),
            contentDescription = stringResource(tab.titleRes),
            tint = textColor,
        )
        Text(
            text = stringResource(tab.titleRes),
            style = SSINGTheme.typography.caption.md11,
            color = textColor,
        )
    }
}
