package com.ssing.presentation.instructorhome.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.core.ui.R
import com.ssing.presentation.instructorhome.Grade

@Composable
fun InstructorReviewCard(
    averageRating: Float,
    grade: Grade,
    achievementRate: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = SSINGTheme.colors.backgroundNormal,
                shape = RoundedCornerShape(12.dp),
            )
            .padding(12.dp),
    ) {
        ReviewSection(
            averageRating = averageRating
        )

        RatingSection(
            progress = achievementRate,
            grade = grade
        )
    }
}

@Composable
private fun ReviewSection(
    averageRating: Float,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        Text(
            text = averageRating.toString(),
            color = SSINGTheme.colors.textAlternative,
            style = SSINGTheme.typography.body.sb16,
        )

        Spacer(modifier = Modifier.width(6.dp))

        repeat(5) { index ->
            val (iconRes, iconColor) = if (index < averageRating) {
                R.drawable.ic_star_filled to Color.Unspecified
            } else {
                R.drawable.ic_star_empty to SSINGTheme.colors.borderDisabled
            }

            Icon(
                painter = painterResource(iconRes),
                contentDescription = null,
                tint = iconColor,
            )
        }
    }
}

@Composable
private fun RatingSection(
    progress: Int,
    grade: Grade,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "강사 등급",
                color = SSINGTheme.colors.textAlternative,
                style = SSINGTheme.typography.caption.sb12,
            )

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                painter = painterResource(grade.icon),
                contentDescription = null,
                tint = Color.Unspecified,
            )

            Spacer(modifier = Modifier.width(2.dp))

            Text(
                text = grade.label,
                style = SSINGTheme.typography.caption.md14,
                color = SSINGTheme.colors.textNormal,
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "달성률",
                color = SSINGTheme.colors.textAlternative,
                style = SSINGTheme.typography.caption.sb12,
            )

            Spacer(modifier = Modifier.weight(1f))

            LinearProgressBar(
                progress = progress,
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "${progress}%",
                style = SSINGTheme.typography.caption.md14,
                color = SSINGTheme.colors.textNormal,
            )
        }
    }
}

@Composable
private fun LinearProgressBar(
    progress: Int,
    modifier: Modifier = Modifier,
) {
    val progressBarLength = 207f

    Box(
        modifier = modifier
            .height(10.dp)
            .width(progressBarLength.dp)
            .background(
                color = SSINGTheme.colors.borderDisabled,
                shape = CircleShape,
            ),
    ) {
        Box(
            modifier = Modifier
                .height(10.dp)
                .width(((progressBarLength/100)*progress).dp)
                .background(
                    color = SSINGTheme.colors.primaryNormal,
                    shape = CircleShape,
                ),
        ) {}
    }
}

@Preview
@Composable
private fun InstructorReviewCardPreview() {
    SSINGTheme {
        InstructorReviewCard(
            averageRating = 3f,
            grade = Grade.Grade4,
            achievementRate = 88,
        )
    }
}