package com.ssing.core.ui.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * clip + background(press 반응) + clickable + padding 기본값을 캡슐화한 버튼 구현 베이스.
 *
 * @param defaultColor 기본 배경색
 * @param pressedColor 눌렸을 때 배경색
 * @param onClick 클릭 시 실행될 콜백
 * @param enabled 버튼 활성 여부
 * @param content 버튼 내부에 배치할 텍스트/아이콘 등
 */
@Composable
internal fun SsingBasicButton(
    defaultColor: Color,
    pressedColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable (BoxScope.() -> Unit),
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(if (isPressed) pressedColor else defaultColor)
            .clickable(
                enabled = enabled,
                onClick = onClick,
                interactionSource = interactionSource,
                indication = null,
            )
            .padding(all = 16.dp),
    ) {
        content()
    }
}
