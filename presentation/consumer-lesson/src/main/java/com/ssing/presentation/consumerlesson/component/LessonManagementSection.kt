package com.ssing.presentation.consumerlesson.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.common.component.SsingButton
import com.ssing.core.ui.common.component.SsingButtonStyle
import com.ssing.core.ui.designsystem.theme.SSINGTheme

@Composable
internal fun LessonManagementSection(
    leftButtonText: String,
    onLeftClick: () -> Unit,
    rightButtonText: String,
    onRightClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ContentSection(
        titleText = "강습 관리",
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            SsingButton(
                text = leftButtonText,
                onClick = onLeftClick,
                style = SsingButtonStyle.RED,
                modifier = Modifier.weight(1f),
            )
            SsingButton(
                text = rightButtonText,
                onClick = onRightClick,
                style = SsingButtonStyle.GRAY,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LessonManagementSectionPreview() {
    SSINGTheme {
        LessonManagementSection(
            leftButtonText = "강습 취소",
            onLeftClick = {},
            rightButtonText = "채팅방",
            onRightClick = {},
        )
    }
}