package com.ssing.core.ui.common.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.R
import com.ssing.core.ui.designsystem.component.SsingBasicButton
import com.ssing.core.ui.designsystem.theme.SSINGTheme

@Composable
fun SsingPlusButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val borderColor = SSINGTheme.colors.borderNormal

    SsingBasicButton(
        defaultColor = SSINGTheme.colors.backgroundNormal,
        pressedColor = SSINGTheme.colors.borderAlternative,
        onClick = onClick,
        modifier = modifier.drawWithContent {
            drawContent()
            drawRoundRect(
                color = borderColor,
                cornerRadius = CornerRadius(12.dp.toPx()),
                style = Stroke(
                    width = 1.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(
                        intervals = floatArrayOf(3.dp.toPx(), 3.dp.toPx()),
                        phase = 0f,
                    ),
                ),
            )
        },
        enabled = enabled,
    ) {
        Row(
            modifier = Modifier.align(Alignment.Center),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_plus_small),
                contentDescription = null,
                tint = SSINGTheme.colors.textAlternative,
            )

            Text(
                text = text,
                color = SSINGTheme.colors.textAlternative,
                style = SSINGTheme.typography.caption.sb14,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SsingPlusButtonPreview() {
    SSINGTheme {
        SsingPlusButton(
            text = "text",
            onClick = {},
            modifier = Modifier
                .width(328.dp)
                .padding(all = 20.dp),
        )
    }
}
