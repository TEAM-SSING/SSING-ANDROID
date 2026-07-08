package com.ssing.presentation.auth.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.core.ui.extension.noRippleClickable

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
        modifier = modifier.noRippleClickable(onClick = onClick),
        color = color,
    )
}

@Preview(showBackground = true)
@Composable
private fun InfoTextPreview(){
    SSINGTheme{
        Column() {
            InfoText(
                text = "이용약관",
                onClick = {},
            )

            InfoText(
                text = "이용약관",
                onClick = {},
                color = SSINGTheme.colors.primaryAlternative,
                modifier = Modifier.background(color = SSINGTheme.colors.primaryNormal)
            )
        }

    }
}

