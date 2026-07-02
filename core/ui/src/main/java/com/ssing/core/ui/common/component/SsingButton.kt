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

enum class SsingButtonType {
    BLUE, GRAY, RED
}

private val SsingButtonType.textColor: Color
    @Composable
    get() = when (this) {
        SsingButtonType.BLUE -> White
        SsingButtonType.GRAY -> SSINGTheme.colors.textNormal
        SsingButtonType.RED -> SSINGTheme.colors.accentRedNormal
    }

private val SsingButtonType.defaultColor: Color
    @Composable
    get() = when (this) {
        SsingButtonType.BLUE -> SSINGTheme.colors.primaryNormal
        SsingButtonType.GRAY -> SSINGTheme.colors.borderDisabled
        SsingButtonType.RED -> SSINGTheme.colors.accentRedAlternative
    }

private val SsingButtonType.pressedColor: Color
    @Composable
    get() = when (this) {
        SsingButtonType.BLUE -> Blue600
        SsingButtonType.GRAY -> SSINGTheme.colors.borderAlternative
        SsingButtonType.RED -> Red200
    }

/**
 * 기본 텍스트 버튼.
 *
 * @param text 버튼에 표시할 텍스트
 * @param onClick 클릭 시 실행될 콜백
 * @param type 버튼 색상 스타일 (배경색, 텍스트색 결정)
 * @param enabled 버튼 활성 여부
 */
@Composable
fun SsingButton(
    text: String,
    onClick: () -> Unit,
    type: SsingButtonType,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    SsingBasicButton(
        defaultColor = type.defaultColor,
        pressedColor = type.pressedColor,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled
    ) {
        Text(
            text = text,
            color = type.textColor,
            style = SSINGTheme.typography.body.sb16,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

private class SsingButtonPreviewProvider: PreviewParameterProvider<SsingButtonType> {
    override val values: Sequence<SsingButtonType>
        get() = SsingButtonType.entries.asSequence()
}

@Preview
@Composable
private fun SsingButtonPreview(
    @PreviewParameter(SsingButtonPreviewProvider::class) type: SsingButtonType,
) {
    SSINGTheme {
        SsingButton(
            text = type.name,
            onClick = {},
            type = type,
            modifier = Modifier.width(328.dp)
        )
    }
}
