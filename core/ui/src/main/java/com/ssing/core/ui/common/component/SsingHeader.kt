package com.ssing.core.ui.common.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.designsystem.theme.SSINGTheme

/**
 * 화면 상단에 제목과 부가 설명을 표시하는 헤더 공통 컴포넌트입니다.
 *
 * @param title 헤더에 표시할 제목
 * @param modifier Composable에 적용할 Modifier
 * @param subText 제목 아래에 표시할 부가 설명 (null이면 표시하지 않음)
 */
@Composable
fun SsingHeader(
    title: String,
    modifier: Modifier = Modifier,
    subText: String? = null,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = title,
            style = SSINGTheme.typography.body.sb20,
            color = SSINGTheme.colors.textNormal,
        )
        subText?.let {
            Text(
                text = it,
                style = SSINGTheme.typography.caption.md14,
                color = SSINGTheme.colors.textAlternative,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SsingHeaderPreview() {
    SsingHeader(
        title = "title",
        subText = "sub",
        modifier = Modifier.padding(vertical = 20.dp),
    )
}

