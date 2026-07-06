package com.ssing.core.ui.common.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.R
import com.ssing.core.ui.designsystem.component.SsingBasicButton
import com.ssing.core.ui.designsystem.component.UrlImage
import com.ssing.core.ui.designsystem.theme.SSINGTheme

@Composable
fun InstructorProfileButton(
    name: String,
    age: Int,
    gender: String,
    level: String,
    imageUrl: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    SsingBasicButton(
        defaultColor = SSINGTheme.colors.backgroundNormal,
        pressedColor = SSINGTheme.colors.borderAlternative,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        defaultBorderColor = SSINGTheme.colors.borderAlternative,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ProfileSection(
                name = name,
                age = age,
                gender = gender,
                level = level,
                imageUrl = imageUrl,
                modifier = Modifier.weight(1f),
            )

            ButtonSection()
        }
    }
}

@Composable
private fun ProfileSection(
    name: String,
    age: Int,
    gender: String,
    level: String,
    imageUrl: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        UrlImage(
            url = imageUrl,
            modifier = Modifier
                .clip(CircleShape)
                .width(44.dp)
                .aspectRatio(1f),
            contentScale = ContentScale.Crop,
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Text(
                text = name,
                color = SSINGTheme.colors.textNormal,
                style = SSINGTheme.typography.caption.sb14,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "${age}세",
                    color = SSINGTheme.colors.textNormal,
                    style = SSINGTheme.typography.caption.md14,
                )

                Spacer(Modifier.width(4.dp))

                Text(
                    text = gender,
                    color = SSINGTheme.colors.primaryNormal,
                    style = SSINGTheme.typography.caption.md14,
                )

                Spacer(Modifier.width(8.dp))

                SsingChip(
                    text = level,
                    style = SsingChipStyle.BLUE,
                )
            }
        }
    }
}

@Composable
private fun ButtonSection(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "프로필 보기",
            color = SSINGTheme.colors.textStrong,
            style = SSINGTheme.typography.caption.md12,
        )

        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_right),
            contentDescription = null,
            tint = SSINGTheme.colors.textNormal,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun InstructorProfileButtonPreview() {
    SSINGTheme {
        InstructorProfileButton(
            name = "김씽씽 강사",
            age = 27,
            gender = "남",
            level = "범고래",
            imageUrl = "",
            onClick = {},
            modifier = Modifier
                .padding(all = 20.dp)
                .width(328.dp),
        )
    }
}
