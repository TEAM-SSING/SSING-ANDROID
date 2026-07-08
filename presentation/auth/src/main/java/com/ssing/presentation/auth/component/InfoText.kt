package com.ssing.presentation.auth.component

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ssing.core.ui.designsystem.theme.SSINGTheme

@Composable
internal fun InfoText(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = SSINGTheme.colors.textAlternative,
) {
    Text(
        text = text,
        style = SSINGTheme.typography.caption.md12,
        modifier = modifier.clickable(onClick = onClick),
        color = color,
    )
}
