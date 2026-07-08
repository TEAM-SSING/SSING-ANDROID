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
import androidx.compose.foundation.layout.wrapContentHeight
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
import kotlinx.collections.immutable.ImmutableList
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.ssing.core.ui.util.ssingDateFormatter
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDateTime

sealed interface HomeLessonCardState {
    data class Reservation(
        val chip: String,
        val displayText: String,
        val location: String,
        val date: LocalDateTime,
        val status: Status
    ) : HomeLessonCardState {
        sealed interface Status {
            data object Default : Status
            data object Matched : Status
            data object Matching : Status
        }
    }

    data object Empty : HomeLessonCardState
}

@Composable
fun HomeLessonCardList(
    states: ImmutableList<HomeLessonCardState>,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ){
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
                onClick = onCardClick,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                space = 4.dp,
                alignment = Alignment.CenterHorizontally,
            ),
        ) {
            if (pagerState.pageCount > 1) {
                repeat(pagerState.pageCount) { iteration ->
                    val color = if (pagerState.currentPage == iteration) SSINGTheme.colors.borderStrong else SSINGTheme.colors.borderAlternative
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
private fun HomeLessonCard(
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
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = 5.dp),
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
                modifier = Modifier.fillMaxWidth()
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
) {
    Row(
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

@Composable
private fun Modifier.lessonCardBackground() = this
    .fillMaxWidth()
    .roundedBackgroundWithBorder(
        shape = RoundedCornerShape(12.dp),
        backgroundColor = SSINGTheme.colors.backgroundNormal,
        borderColor = SSINGTheme.colors.borderAlternative,
        borderWidth = 1.dp,
    )

@Preview
@Composable
private fun HomeLessonEmptyCardPreview() {
    SSINGTheme {
        HomeLessonCard(
            state = HomeLessonCardState.Empty,
            onClick = {},
            modifier = Modifier.width(328.dp),
        )
    }
}

@Preview
@Composable
private fun HomeLessonMatchingCardPreview() {
    SSINGTheme {
        HomeLessonCard(
            state = HomeLessonCardState.Reservation(
                chip = "Now",
                displayText = "매칭중",
                date = LocalDateTime.of(2025, 7, 15, 19, 0),
                location = "하이원",
                status = Status.Matching
            ),
            onClick = {},
            modifier = Modifier.width(328.dp),
        )
    }
}

@Preview
@Composable
private fun HomeLessonMatchedCardPreview() {
    SSINGTheme {
        HomeLessonCard(
            state = HomeLessonCardState.Reservation(
                chip = "Now",
                displayText = "김OO님 팀 3명",
                date = LocalDateTime.of(2025, 7, 15, 19, 0),
                location = "하이원",
                status = Status.Matched,
            ),
            onClick = {},
            modifier = Modifier.width(328.dp),
        )
    }
}

@Preview
@Composable
private fun HomeLessonCardPreview() {
    SSINGTheme {
        HomeLessonCard(
            state = HomeLessonCardState.Reservation(
                chip = "D-2",
                displayText = "김OO님 팀 3명",
                date = LocalDateTime.of(2025, 7, 15, 19, 0),
                location = "하이원",
                status = Status.Default
            ),
            onClick = {},
            modifier = Modifier.width(328.dp),
        )
    }
}

private class HomeLessonCardPreviewProvider : PreviewParameterProvider<ImmutableList<HomeLessonCardState>> {
    override val values = sequenceOf(
        persistentListOf(HomeLessonCardState.Empty),
        persistentListOf(
            HomeLessonCardState.Reservation(
                chip = "Now",
                displayText = "김OO님 팀 3명",
                location = "하이원",
                date = LocalDateTime.of(2025, 7, 15, 19, 0),
                status = Status.Matching,
            ),
            HomeLessonCardState.Reservation(
                chip = "Now",
                displayText = "김OO님 팀 3명",
                location = "하이원",
                date = LocalDateTime.of(2025, 7, 15, 19, 0),
                status = Status.Default,
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
            onCardClick = {},
            modifier = Modifier,
        )
    }
}