package com.ssing.core.ui.common.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.R
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.core.ui.extension.roundedBackgroundWithBorder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

sealed interface HomeLessonCardState {
    data class Reservation(
        val title: String,
        val location: String,
        val dDay: Int,
        val date: LocalDateTime,
    ): HomeLessonCardState

    data object Empty: HomeLessonCardState
}

@Composable
fun SsingHomeLessonCard(
    state: HomeLessonCardState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (state) {
        is HomeLessonCardState.Reservation -> {
            Column(
                modifier = modifier
                    .roundedBackgroundWithBorder(
                        shape = RoundedCornerShape(12.dp),
                        backgroundColor = SSINGTheme.colors.backgroundNormal,
                        borderColor = SSINGTheme.colors.borderAlternative,
                        borderWidth = 1.dp,
                    )
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                LessonInfoSection(
                    title = state.title,
                    location = state.location,
                    date = state.date.ssingDateFormatter(),
                    dDay = state.dDay,
                )

                SsingButton(
                    text = "강습 상세보기",
                    onClick = onClick,
                    style = SsingButtonStyle.GRAY,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }

        HomeLessonCardState.Empty -> {
            Row(
                modifier = modifier
                    .roundedBackgroundWithBorder(
                        shape = RoundedCornerShape(12.dp),
                        backgroundColor = SSINGTheme.colors.backgroundNormal,
                        borderColor = SSINGTheme.colors.borderAlternative,
                        borderWidth = 1.dp,
                    )
                    .padding(
                        vertical = 24.dp,
                        horizontal = 16.dp
                    )
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                EmptyLessonInfoSection(
                    modifier = Modifier.weight(1f)
                )

                Image(
                    painter = painterResource(id = R.drawable.img_ski),
                    contentDescription = null,
                    modifier = Modifier.size(86.dp),
                )
            }
        }
    }
}

@Composable
private fun LessonInfoSection(
    title: String,
    location: String,
    date: String,
    dDay: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            SsingChip(
                text = "D-${dDay}",
                style = SsingChipStyle.BLUE,
            )

            Text(
                text = title,
                style = SSINGTheme.typography.body.sb20,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
            )

            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                InfoRow(
                    iconRes = R.drawable.ic_reservation_16,
                    text = date,
                )
                
                Icon(
                    imageVector =  ImageVector.vectorResource(R.drawable.ic_divide_line),
                    contentDescription = null,
                    tint = SSINGTheme.colors.borderAlternative,
                )

                InfoRow(
                    iconRes = R.drawable.ic_location_16,
                    text = location,
                )
            }
        }

        Image(
            painter = painterResource(id = R.drawable.img_ski),
            contentDescription = null,
            modifier = Modifier.size(86.dp),
        )
    }
}

@Composable
private fun InfoRow(
    @DrawableRes iconRes: Int,
    text: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(iconRes),
            contentDescription = null,
            tint = SSINGTheme.colors.textAlternative,
        )

        Text(
            text = text,
            style = SSINGTheme.typography.caption.md12,
            color = SSINGTheme.colors.textAlternative,
        )
    }
}

@Composable
private fun EmptyLessonInfoSection(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_reservation_24),
            contentDescription = null,
            tint = SSINGTheme.colors.textAlternative,
            modifier = Modifier
                .padding(bottom = 6.dp),
        )

        Text(
            text = "아직 예약된 강습이 없어요.",
            style = SSINGTheme.typography.body.sb16,
            color = SSINGTheme.colors.textAlternative,
        )

        Text(
            text = "새 강습을 예약해보세요!",
            style = SSINGTheme.typography.caption.md14,
            color = SSINGTheme.colors.textDisabled,
        )
    }
}

fun LocalDateTime.ssingDateFormatter(): String {
    return this.format(DateTimeFormatter.ofPattern("yyyy. MM. dd (E) HH:mm"))
}

@Preview
@Composable
private fun SsingHomeLessonCardPreview() {
    SSINGTheme {
        SsingHomeLessonCard(
            state = HomeLessonCardState.Reservation(
                title = "text",
                date = LocalDateTime.of(2025, 7, 15, 19, 0),
                location = "하이원",
                dDay = 2,
            ),
            onClick = {},
            modifier = Modifier.width(320.dp),
        )
    }
}

@Preview
@Composable
private fun SsingHomeLessonEmptyCardPreview() {
    SSINGTheme {
        SsingHomeLessonCard(
            state = HomeLessonCardState.Empty,
            onClick = {},
            modifier = Modifier.width(320.dp),
        )
    }
}