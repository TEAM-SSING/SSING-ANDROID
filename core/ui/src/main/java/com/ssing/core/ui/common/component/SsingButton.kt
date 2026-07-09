package com.ssing.core.ui.common.component

import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.designsystem.component.SsingBasicButton
import com.ssing.core.ui.designsystem.theme.Blue600
import com.ssing.core.ui.designsystem.theme.Red200
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.core.ui.designsystem.theme.White

enum class SsingButtonStyle {
    BLUE, GRAY, RED
}

@Composable
private fun SsingButtonStyle.textColor(enabled: Boolean): Color {
    if (!enabled) return SSINGTheme.colors.textAlternative
    return when (this) {
        SsingButtonStyle.BLUE -> White
        SsingButtonStyle.GRAY -> SSINGTheme.colors.textNormal
        SsingButtonStyle.RED -> SSINGTheme.colors.accentRedNormal
    }
}

private val SsingButtonStyle.defaultColor: Color
    @Composable
    get() = when (this) {
        SsingButtonStyle.BLUE -> SSINGTheme.colors.primaryNormal
        SsingButtonStyle.GRAY -> SSINGTheme.colors.borderDisabled
        SsingButtonStyle.RED -> SSINGTheme.colors.accentRedAlternative
    }

private val SsingButtonStyle.pressedColor: Color
    @Composable
    get() = when (this) {
        SsingButtonStyle.BLUE -> Blue600
        SsingButtonStyle.GRAY -> SSINGTheme.colors.borderAlternative
        SsingButtonStyle.RED -> Red200
    }

/**
 * 기본 텍스트 버튼.
 *
 * @param text 버튼에 표시할 텍스트
 * @param onClick 클릭 시 실행될 콜백
 * @param style 버튼 색상 스타일 (배경색, 텍스트색 결정)
 * @param enabled 버튼 활성 여부
 */
@Composable
fun SsingButton(
    text: String,
    onClick: () -> Unit,
    style: SsingButtonStyle,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val disabledColor = SSINGTheme.colors.borderDisabled
    SsingBasicButton(
        defaultColor = if (enabled) style.defaultColor else disabledColor,
        pressedColor = if (enabled) style.pressedColor else disabledColor,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled
    ) {
        Text(
            text = text,
            color = style.textColor(enabled),
            style = SSINGTheme.typography.body.sb16,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

private class SsingButtonPreviewProvider : PreviewParameterProvider<SsingButtonStyle> {
    override val values: Sequence<SsingButtonStyle>
        get() = SsingButtonStyle.entries.asSequence()
}

@Preview
@Composable
private fun SsingButtonPreview(
    @PreviewParameter(SsingButtonPreviewProvider::class) type: SsingButtonStyle,
) {
    SSINGTheme {
        SsingButton(
            text = type.name,
            onClick = {},
            style = type,
            modifier = Modifier.width(328.dp)
        )
    }
}

@Preview
@Composable
private fun SsingButtonGrayDisabledPreview() {
    SSINGTheme {
        SsingButton(
            text = "GRAY-DISABLED",
            onClick = {},
            style = SsingButtonStyle.GRAY,
            modifier = Modifier.width(328.dp),
            enabled = false,
        )
    }
}
