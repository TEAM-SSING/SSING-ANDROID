package com.ssing.core.ui.common.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import kotlinx.collections.immutable.ImmutableList
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.ssing.core.ui.util.ssingDateFormatter
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDateTime

sealed interface HomeLessonCardState
    data class Reservation(
        val lessonId: Long,
        val chip: String,
        val displayText: String,
        val location: String,
        val date: LocalDateTime,
        val imageRes: Int,
        val status: Status,
    ) : HomeLessonCardState {
        sealed interface Status {
            data object Default : Status
            data object Matched : Status
            data object Matching : Status
        }
    }
    data object Empty : HomeLessonCardState


@Composable
fun HomeLessonCardList(
    states: ImmutableList<HomeLessonCardState>,
    onButtonClick: (Reservation) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        val pagerState = rememberPagerState(
            pageCount = { states.size }
        )

        HorizontalPager(
            modifier = Modifier.fillMaxWidth(),
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 16.dp),
            pageSpacing = 8.dp,
        ) { page ->
            HomeLessonCard(
                state = states[page],
                onButtonClick = onButtonClick,
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (pagerState.pageCount > 1) {
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    space = 4.dp,
                    alignment = Alignment.CenterHorizontally,
                ),
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val color =
                        if (pagerState.currentPage == iteration) SSINGTheme.colors.borderStrong else SSINGTheme.colors.borderAlternative
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(color)
                            .size(5.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun HomeLessonEmptyCard(
    modifier: Modifier = Modifier,
) {
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
            modifier = Modifier
                .weight(1f)
                .padding(bottom = 5.dp),
        )

        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_empty_reservation_76),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(76.dp),
        )
    }
}

@Composable
private fun HomeLessonReservationCard(
    state: Reservation,
    onButtonClick: (Reservation) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .lessonCardBackground()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        LessonInfoSection(state = state)

        SsingButton(
            text = if (state.status is Reservation.Status.Default) "강습 상세보기" else "이어보기",
            onClick = { onButtonClick(state) },
            style = SsingButtonStyle.GRAY,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun HomeLessonCard(
    state: HomeLessonCardState,
    onButtonClick: (Reservation) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (state) {
        is Empty -> {
            HomeLessonEmptyCard(
                modifier = modifier,
            )
        }

        is Reservation -> {
            HomeLessonReservationCard(
                state = state,
                onButtonClick = onButtonClick,
                modifier = modifier,
            )
        }
    }
}

@Composable
private fun LessonInfoSection(
    state: Reservation,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
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
                text = state.displayText,
                style = SSINGTheme.typography.body.sb20,
                color = SSINGTheme.colors.textNormal,
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                InfoRow(
                    iconRes = R.drawable.ic_reservation_16,
                    text = state.date.ssingDateFormatter(),
                )

                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_divide_line),
                    contentDescription = null,
                    tint = SSINGTheme.colors.borderAlternative,
                )

                InfoRow(
                    iconRes = R.drawable.ic_location_16,
                    text = state.location,
                    hasGap = false,
                )
            }
        }

        Image(
            painter = painterResource(id = state.imageRes),
            contentDescription = null,
            modifier = Modifier.size(86.dp),
        )
    }
}

@Composable
private fun InfoRow(
    @DrawableRes iconRes: Int,
    text: String,
    hasGap: Boolean = true,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(iconRes),
            contentDescription = null,
            tint = SSINGTheme.colors.textAlternative,
        )

        if (hasGap) Spacer(modifier = Modifier.width(2.dp))

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
            imageVector = ImageVector.vectorResource(R.drawable.ic_plus_large),
            contentDescription = null,
            tint = SSINGTheme.colors.textDisabled,
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "아직 예약된 강습이 없어요",
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

@Composable
private fun Modifier.lessonCardBackground() = this
    .fillMaxWidth()
    .background(
        shape = RoundedCornerShape(12.dp),
        color = SSINGTheme.colors.backgroundNormal,
    )

@Preview
@Composable
private fun HomeLessonEmptyCardPreview() {
    SSINGTheme {
        HomeLessonCard(
            state = Empty,
            onButtonClick = {},
            modifier = Modifier.width(328.dp),
        )
    }
}

@Preview
@Composable
private fun HomeLessonMatchingCardPreview() {
    SSINGTheme {
        HomeLessonCard(
            state = Reservation(
                lessonId = 1,
                chip = "Now",
                displayText = "매칭중",
                date = LocalDateTime.of(2025, 7, 15, 19, 0),
                location = "하이원",
                imageRes = R.drawable.img_ski_86,
                status = Reservation.Status.Matching
            ),
            onButtonClick = {},
            modifier = Modifier.width(328.dp),
        )
    }
}

@Preview
@Composable
private fun HomeLessonMatchedCardPreview() {
    SSINGTheme {
        HomeLessonCard(
            state = Reservation(
                lessonId = 1,
                chip = "Now",
                displayText = "김OO님 팀 3명",
                date = LocalDateTime.of(2025, 7, 15, 19, 0),
                location = "하이원",
                imageRes = R.drawable.img_ski_86,
                status = Reservation.Status.Matched,
            ),
            onButtonClick = {},
            modifier = Modifier.width(328.dp),
        )
    }
}

@Preview
@Composable
private fun HomeLessonCardPreview() {
    SSINGTheme {
        HomeLessonCard(
            state = Reservation(
                lessonId = 1,
                chip = "D-2",
                displayText = "김OO님 팀 3명",
                date = LocalDateTime.of(2025, 7, 15, 19, 0),
                location = "하이원",
                imageRes = R.drawable.img_snowboard_86,
                status = Reservation.Status.Default
            ),
            onButtonClick = {},
            modifier = Modifier.width(328.dp),
        )
    }
}

private class HomeLessonCardPreviewProvider(Status: Any) :
    PreviewParameterProvider<ImmutableList<HomeLessonCardState>> {
    override val values = sequenceOf(
        persistentListOf(Empty),
        persistentListOf(
            Reservation(
                lessonId = 1,
                chip = "Now",
                displayText = "김OO님 팀 3명",
                location = "하이원",
                date = LocalDateTime.of(2025, 7, 15, 19, 0),
                imageRes = R.drawable.img_snowboard_86,
                status = Reservation.Status.Matching,
            ),
            Reservation(
                lessonId = 1,
                chip = "D-3",
                displayText = "김OO님 팀 3명",
                location = "지산리조트",
                date = LocalDateTime.of(2026, 7, 11, 19, 0),
                imageRes = R.drawable.img_snowboard_86,
                status = Reservation.Status.Default,
            ),
        ),
    )
}

@Preview
@Composable
private fun HomeLessonCardListPreview(
    @PreviewParameter(HomeLessonCardPreviewProvider::class) states: ImmutableList<HomeLessonCardState>,
) {
    SSINGTheme {
        HomeLessonCardList(
            states = states,
            onButtonClick = {},
            modifier = Modifier,
        )
    }
}
