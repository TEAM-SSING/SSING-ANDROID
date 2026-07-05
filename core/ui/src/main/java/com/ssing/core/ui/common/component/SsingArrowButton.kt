package com.ssing.core.ui.common.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.R
import com.ssing.core.ui.designsystem.component.SsingBasicButton
import com.ssing.core.ui.designsystem.theme.SSINGTheme

@Composable
fun SsingArrowButton(
    text: String,
    chipText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    chipStyle: SsingChipStyle = SsingChipStyle.BLUE,
) {
    SsingBasicButton(
        defaultColor = SSINGTheme.colors.backgroundNormal,
        pressedColor = SSINGTheme.colors.borderAlternative,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        defaultBorderColor = SSINGTheme.colors.borderAlternative,
        pressedBorderColor = SSINGTheme.colors.borderNormal,
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = text,
                color = SSINGTheme.colors.textNormal,
                style = SSINGTheme.typography.caption.sb14,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            SsingChip(
                text = chipText,
                style = chipStyle,
            )

            Spacer(Modifier.width(4.dp))

            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_right),
                contentDescription = null,
                tint = SSINGTheme.colors.textNormal,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SsingArrowButtonPreview() {
    SSINGTheme {
        SsingArrowButton(
            text = "text",
            chipText = "text",
            onClick = {},
            modifier = Modifier
                .width(328.dp)
                .padding(all = 20.dp),
        )
    }
}
