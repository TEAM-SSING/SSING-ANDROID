package com.ssing.core.ui.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.designsystem.theme.Blue50
import com.ssing.core.ui.designsystem.theme.SSINGTheme

private val Boolean.selectButtonBackground: Color
    @Composable
    get() = if (this) Blue50 else SSINGTheme.colors.backgroundNormal

private val Boolean.selectButtonBorder: Color
    @Composable
    get() = if (this) SSINGTheme.colors.primaryNormal else SSINGTheme.colors.borderAlternative

private val Boolean.selectButtonText: Color
    @Composable
    get() = if (this) SSINGTheme.colors.primaryNormal else SSINGTheme.colors.textAlternative

/**
 * 선택형 옵션 버튼 공통 컴포넌트입니다.
 * 선택 여부에 따라 배경/테두리/텍스트 색상이 변경됩니다.
 * @param text 버튼에 표시할 텍스트
 * @param isSelected 선택 여부
 * @param onClick 클릭 시 호출되는 콜백
 * @param modifier Composable에 적용할 Modifier
 */

@Composable
fun SsingSelectButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(12.dp)

    Box(
        modifier = modifier
            .clip(shape)
            .background(color = isSelected.selectButtonBackground, shape = shape)
            .border(width = 1.dp, color = isSelected.selectButtonBorder, shape = shape)
            .clickable(onClick = onClick)
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = SSINGTheme.typography.caption.sb14,
            color = isSelected.selectButtonText,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SsingSelectButtonPreview() {
    var isSelected by remember { mutableStateOf(false) }

    SsingSelectButton(
        text = "옵션",
        isSelected = isSelected,
        onClick = { isSelected = !isSelected },
        modifier = Modifier.width(162.dp),
    )
}
