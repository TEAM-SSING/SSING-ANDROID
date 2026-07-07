package com.ssing.core.ui.common.component

import android.R.attr.text
import android.system.Os.stat
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.ssing.core.ui.common.component.HomeLessonCardState.Reservation.Status
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.core.ui.extension.roundedBackgroundWithBorder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

sealed interface HomeLessonCardState {
    data class Reservation(
        val chip: String,
        val title: String,
        val location: String,
        val date: LocalDateTime,
        val status: Status
    ): HomeLessonCardState {
        sealed interface Status {
            data class Default(val member: Int): Status
            data class Matched(val member: Int): Status
            data object Matching: Status
        }
    }

    data object Empty: HomeLessonCardState
}

@Composable
private fun Modifier.lessonCardBackground() = this
    .fillMaxWidth()
    .roundedBackgroundWithBorder(
        shape = RoundedCornerShape(12.dp),
        backgroundColor = SSINGTheme.colors.backgroundNormal,
        borderColor = SSINGTheme.colors.borderAlternative,
        borderWidth = 1.dp,
    )

@Composable
fun SsingHomeLessonCard(
    state: HomeLessonCardState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (state) {
        is HomeLessonCardState.Empty -> {
            Row(
                modifier = modifier
                    .lessonCardBackground()
                    .padding(
                        vertical = 24.dp,
                        horizontal = 16.dp
                    ),
                verticalAlignment = Alignment.Bottom,
            ) {
                EmptyLessonInfoSection(
                    modifier = Modifier.weight(1f)
                )

                Image(
                    painter = painterResource(id = R.drawable.img_ski_66),
                    contentDescription = null,
                    modifier = Modifier.size(66.dp),
                )
            }
        }

        is HomeLessonCardState.Reservation -> {
            Column(
                modifier = modifier
                    .lessonCardBackground()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                LessonInfoSection(state = state)

                SsingButton(
                    text = if (state.status is Status.Default) "강습 상세보기" else "이어보기",
                    onClick = onClick,
                    style = SsingButtonStyle.GRAY,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

@Composable
private fun LessonInfoSection(
    state: HomeLessonCardState.Reservation,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            SsingChip(
                text = state.chip,
                style = SsingChipStyle.BLUE,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = when (state.status) {
                    is Status.Matching -> "매칭중"
                    is Status.Default -> "${state.title}님 팀 ${state.status.member}명"
                    is Status.Matched -> "${state.title}님 팀 ${state.status.member}명"
                },
                style = SSINGTheme.typography.body.sb20,
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                InfoRow(
                    iconRes = R.drawable.ic_reservation_16,
                    text = state.date.ssingDateFormatter(),
                )
                
                Icon(
                    imageVector =  ImageVector.vectorResource(R.drawable.ic_divide_line),
                    contentDescription = null,
                    tint = SSINGTheme.colors.borderAlternative,
                )

                InfoRow(
                    iconRes = R.drawable.ic_location_16,
                    text =  state.location,
                )
            }
        }

        Image(
            painter = painterResource(id = R.drawable.img_ski_66),
            contentDescription = null,
            modifier = Modifier.size(66.dp),
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
        )

        Spacer(modifier = Modifier.height(6.dp))

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

private fun LocalDateTime.ssingDateFormatter(): String {
    return this.format(DateTimeFormatter.ofPattern("yyyy. MM. dd (E) HH:mm", Locale.KOREAN))
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

@Preview
@Composable
private fun SsingHomeLessonMatchingCardPreview() {
    SSINGTheme {
        SsingHomeLessonCard(
            state = HomeLessonCardState.Reservation(
                chip = "Now",
                title = "매칭중",
                date = LocalDateTime.of(2025, 7, 15, 19, 0),
                location = "하이원",
                status = Status.Matching
            ),
            onClick = {},
            modifier = Modifier.width(320.dp),
        )
    }
}

@Preview
@Composable
private fun SsingHomeLessonMatchedCardPreview() {
    SSINGTheme {
        SsingHomeLessonCard(
            state = HomeLessonCardState.Reservation(
                chip = "Now",
                title = "김OO",
                date = LocalDateTime.of(2025, 7, 15, 19, 0),
                location = "하이원",
                status = Status.Matched(member = 3),
            ),
            onClick = {},
            modifier = Modifier.width(320.dp),
        )
    }
}

@Preview
@Composable
private fun SsingHomeLessonCardPreview() {
    SSINGTheme {
        SsingHomeLessonCard(
            state = HomeLessonCardState.Reservation(
                chip = "D-2",
                title = "김OO",
                date = LocalDateTime.of(2025, 7, 15, 19, 0),
                location = "하이원",
                status = Status.Default(member = 3)
            ),
            onClick = {},
            modifier = Modifier.width(320.dp),
        )
    }
}