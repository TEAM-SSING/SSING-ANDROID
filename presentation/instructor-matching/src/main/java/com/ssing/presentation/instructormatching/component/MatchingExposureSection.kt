package com.ssing.presentation.instructormatching.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.designsystem.theme.SSINGTheme

@Composable
internal fun MatchingExposureSection(
    label: String,
    modifier: Modifier = Modifier,
    labelSuffix: (@Composable RowScope.() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = label,
                style = SSINGTheme.typography.caption.sb12,
                color = SSINGTheme.colors.textAlternative,
            )
            Spacer(modifier = Modifier.width(6.dp))
            labelSuffix?.invoke(this)
        }
        content()
    }
}

@Composable
internal fun MultiSelectBadge(modifier: Modifier = Modifier) {
    Text(
        text = "*복수 선택",
        style = SSINGTheme.typography.caption.sb12,
        color = SSINGTheme.colors.primaryNormal,
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
private fun MatchingExposureSectionPreview() {
    SSINGTheme {
        MatchingExposureSection(
            label = "강습 가능 레벨",
            labelSuffix = { MultiSelectBadge() },
        ) {
            Text(text = "content")
        }
    }
}