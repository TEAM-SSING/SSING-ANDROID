package com.ssing.presentation.consumerlesson.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.common.component.SsingButton
import com.ssing.core.ui.common.component.SsingButtonStyle

internal data class LessonActionButton(
    val text: String,
    val onClick: () -> Unit,
)

@Composable
internal fun LessonManagementSection(
    primaryButton: LessonActionButton,
    secondaryButton: LessonActionButton,
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
                text = primaryButton.text,
                onClick = primaryButton.onClick,
                style = SsingButtonStyle.RED,
                modifier = Modifier.weight(1f),
            )
            SsingButton(
                text = secondaryButton.text,
                onClick = secondaryButton.onClick,
                style = SsingButtonStyle.GRAY,
                modifier = Modifier.weight(1f),
            )
        }
    }
}