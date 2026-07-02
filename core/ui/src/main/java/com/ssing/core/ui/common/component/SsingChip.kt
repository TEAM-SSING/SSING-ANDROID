package com.ssing.core.ui.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.designsystem.theme.Blue100
import com.ssing.core.ui.designsystem.theme.Blue50
import com.ssing.core.ui.designsystem.theme.SSINGTheme

/**
 * 짧은 라벨/태그를 표시하는 칩 공통 컴포넌트입니다.
 *
 * @param text 칩에 표시할 텍스트
 * @param style 칩 색상 스타일 (DeepBlue/ Blue / Gray)
 * @param modifier Composable에 적용할 Modifier
 */
@Composable
fun SsingChip(
    text: String,
    style: SsingChipStyle,
    modifier: Modifier = Modifier,
) {
    val (backgroundColor, textColor) = when (style) {
        SsingChipStyle.DEEP_BLUE -> Blue50 to SSINGTheme.colors.primaryStrong
        SsingChipStyle.BLUE -> Blue100 to SSINGTheme.colors.primaryNormal
        SsingChipStyle.GRAY ->
            SSINGTheme.colors.backgroundAlternative to SSINGTheme.colors.textNormal
    }

    Text(
        text = text,
        style = SSINGTheme.typography.caption.sb12,
        color = textColor,
        modifier = modifier
            .background(color = backgroundColor, shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
    )
}


enum class SsingChipStyle {
    DEEP_BLUE,
    BLUE,
    GRAY,
}

@Preview(showBackground = true)
@Composable
private fun SsingChipPreview() {
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        SsingChip(text = "text", style = SsingChipStyle.DEEP_BLUE)
        SsingChip(text = "text", style = SsingChipStyle.BLUE)
        SsingChip(text = "text", style = SsingChipStyle.GRAY)
    }
}
