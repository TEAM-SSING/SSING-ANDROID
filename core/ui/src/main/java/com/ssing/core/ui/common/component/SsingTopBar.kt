package com.ssing.core.ui.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.R
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.core.ui.extension.noRippleClickable

/**
 * 공통 상단바 컴포넌트입니다.
 *
 * [onBack]을 전달하면 좌측에 뒤로가기 버튼이 표시되고(default),
 * 생략하면 타이틀만 표시됩니다(nameOnly).
 *
 * @param title 상단바 타이틀입니다.
 * @param modifier
 * @param backgroundColor 상단바 배경색입니다.
 * @param onBack 뒤로가기 버튼 클릭 콜백입니다. null이면 뒤로가기 버튼이 표시되지 않습니다.
 */
@Composable
fun SsingTopBar(
    title: String,
    modifier: Modifier = Modifier,
    onBack: (() -> Unit)? = null,
    backgroundColor: Color = SSINGTheme.colors.backgroundNormal,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = backgroundColor)
            .padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        if (onBack != null) {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_left),
                contentDescription = "뒤로가기",
                tint = SSINGTheme.colors.textNormal,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .noRippleClickable { onBack() },
            )
        }
        Text(
            text = title,
            style = SSINGTheme.typography.body.sb16,
            color = SSINGTheme.colors.textNormal,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center),
        )
    }
}

private class SsingTopBarPreviewProvider : PreviewParameterProvider<Boolean> {
    override val values = sequenceOf(true, false)
}

@Preview(showBackground = true)
@Composable
private fun SsingTopBarPreview(
    @PreviewParameter(SsingTopBarPreviewProvider::class) hasBack: Boolean,
) {
    SSINGTheme {
        SsingTopBar(
            title = "상단바 타이틀",
            onBack = if (hasBack) ({}) else null,
        )
    }
}
