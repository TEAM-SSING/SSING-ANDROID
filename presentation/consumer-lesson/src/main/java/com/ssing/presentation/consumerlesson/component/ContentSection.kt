package com.ssing.presentation.consumerlesson.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.designsystem.theme.SSINGTheme

@Composable
internal fun ContentSection(
    titleText: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = titleText,
            color = SSINGTheme.colors.textAlternative,
            style = SSINGTheme.typography.caption.sb12,
        )

        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun ContentSectionPreview() {
    SSINGTheme {
        ContentSection(titleText = "강습 정보") {
            Text(text = "content")
        }
    }
}