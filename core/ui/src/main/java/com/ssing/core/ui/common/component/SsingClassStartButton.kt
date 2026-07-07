package com.ssing.core.ui.common.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.R
import com.ssing.core.ui.designsystem.theme.SSINGTheme

@Composable
fun SsingStartClassCard(
    badgeText: String,
    title: String,
    description: String,
    background: Brush,
    @DrawableRes iconRes: Int,
    titleColor: Color,
    descriptionColor: Color,
    chipStyle: SsingChipStyle,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(brush = background),
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier
                .size(width = 90.dp, height = 143.dp)
                .align(Alignment.BottomEnd)
                .offset(x = 20.dp, y = 27.dp),
        )
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(12.dp),
        ) {
            SsingChip(text = badgeText, style = chipStyle)

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = title, style = SSINGTheme.typography.body.sb16, color = titleColor)

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = description,
                style = SSINGTheme.typography.caption.sb12,
                color = descriptionColor
            )
        }
    }
}


@Preview
@Composable
private fun SsingStartClassCardPreview() {
    SSINGTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(17.dp),
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                SsingStartClassCard(
                    badgeText = "text", title = "title", description = "text",
                    background = Brush.linearGradient(listOf(Color.White, Color(0xFFEFF2F8))),
                    iconRes = R.drawable.img_fast,
                    titleColor = SSINGTheme.colors.textNormal,
                    descriptionColor = SSINGTheme.colors.textAlternative,
                    chipStyle = SsingChipStyle.BLUE,
                )

                SsingStartClassCard(
                    badgeText = "text", title = "title", description = "text",
                    background = SolidColor(SSINGTheme.colors.borderDisabled),
                    iconRes = R.drawable.img_fast,
                    titleColor = SSINGTheme.colors.textNormal,
                    descriptionColor = SSINGTheme.colors.textAlternative,
                    chipStyle = SsingChipStyle.BLUE,
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                SsingStartClassCard(
                    badgeText = "text", title = "title", description = "text",
                    background = Brush.linearGradient(
                        listOf(
                            Color(0xFF64AAFF),
                            Color(0xFF3184EA),
                            Color(0xFF357DD5)
                        )
                    ),
                    iconRes = R.drawable.img_fast_dark,
                    titleColor = SSINGTheme.colors.backgroundNormal,
                    descriptionColor = SSINGTheme.colors.primaryAlternative,
                    chipStyle = SsingChipStyle.BLUE,
                )

                SsingStartClassCard(
                    badgeText = "text", title = "title", description = "text",
                    background = SolidColor(Color(0xFF2E6BF0)),
                    iconRes = R.drawable.img_fast_dark,
                    titleColor = SSINGTheme.colors.backgroundNormal,
                    descriptionColor = SSINGTheme.colors.primaryAlternative,
                    chipStyle = SsingChipStyle.BLUE,
                )
            }
        }
    }
}