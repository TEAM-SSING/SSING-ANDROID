package com.ssing.consumer.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.ssing.consumer.ConsumerMainTab
import com.ssing.core.ui.extension.noRippleClickable
import kotlinx.collections.immutable.ImmutableList

// TODO: Color, padding 디자인 사항에 맞게 수정 예정
@Composable
fun ConsumerBottomBar(
    isVisible: Boolean,
    tabs: ImmutableList<ConsumerMainTab>,
    currentTab: ConsumerMainTab?,
    onTabSelected: (ConsumerMainTab) -> Unit,
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
                    color = Color.White,
                )
                .padding(
                    vertical = 10.dp
                )
                .navigationBarsPadding(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            tabs.forEach { tab ->
                key(tab.route) {
                    MainBottomBarItem(
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
private fun MainBottomBarItem(
    tab: ConsumerMainTab,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .noRippleClickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(tab.iconRes),
            contentDescription = stringResource(tab.titleRes),
            tint = Color.Unspecified,
        )

        Text(
            text = stringResource(tab.titleRes),
            color = Color.Black,
        )
    }
}
