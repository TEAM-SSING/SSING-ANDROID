package com.ssing.instructor.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.core.ui.extension.noRippleClickable
import com.ssing.instructor.InstructorMainTab
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun InstructorBottomBar(
    isVisible: Boolean,
    tabs: ImmutableList<InstructorMainTab>,
    currentTab: InstructorMainTab?,
    onTabSelected: (InstructorMainTab) -> Unit,
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
                    shape = RoundedCornerShape(
                        topStart = 12.dp,
                        topEnd = 12.dp,
                    )
                )
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .navigationBarsPadding(),
            horizontalArrangement = Arrangement.spacedBy(32.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            tabs.forEach { tab ->
                key(tab.route) {
                    InstructorBottomBarItem(
                        tab = tab,
                        isSelected = tab == currentTab,
                        onClick = { onTabSelected(tab) },
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
    }
}

@Composable
private fun InstructorBottomBarItem(
    tab: InstructorMainTab,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val (textColor, iconColor, iconRes) = when {
        isSelected -> Triple(
            SSINGTheme.colors.textStrong,
            Color.Unspecified,
            tab.selectedIconRes,
        )

        else -> Triple(
            SSINGTheme.colors.textAlternative,
            Color.Unspecified,
            tab.unselectedIconRes,
        )
    }

    Column(
        modifier = modifier.noRippleClickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(iconRes),
            contentDescription = stringResource(tab.titleRes),
            tint = iconColor,
        )
        Text(
            text = stringResource(tab.titleRes),
            style = SSINGTheme.typography.caption.md11,
            color = textColor,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0XFFF3F4F8)
@Composable
private fun InstructorBottomBarPreview() {
    SSINGTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
        ) {
            InstructorBottomBar(
                isVisible = true,
                tabs = InstructorMainTab.entries.toImmutableList(),
                currentTab = InstructorMainTab.HOME,
                onTabSelected = {},
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
