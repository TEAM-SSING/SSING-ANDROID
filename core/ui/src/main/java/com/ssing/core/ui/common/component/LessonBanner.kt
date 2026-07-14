package com.ssing.core.ui.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.R
import com.ssing.core.ui.designsystem.theme.Blue100
import com.ssing.core.ui.designsystem.theme.Blue50
import com.ssing.core.ui.designsystem.theme.SSINGTheme

sealed interface LessonBannerState {
    data class Before(
        val isInstructorReady: Boolean,
        val participantReadyCount: Int,
        val participantTotalCount: Int,
    ) : LessonBannerState {
        val totalReadyCount: Int
            get() = (participantReadyCount + if (isInstructorReady) 1 else 0).coerceIn(
                0,
                totalCount
            )

        val totalCount: Int
            get() = participantTotalCount + 1
    }

    data class Ongoing(
        val remainingTime: String,
        val elapsedTime: String,
    ) : LessonBannerState

    data class Completed(
        val lessonDate: String,
    ) : LessonBannerState

    data object Canceled : LessonBannerState
}

@Composable
fun LessonBanner(
    lessonBannerState: LessonBannerState,
    modifier: Modifier = Modifier,
    beforeLessonText: String? = null,
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Blue50)
            .padding(
                top = 16.dp,
                start = 16.dp,
                end = 16.dp,
            )
    ) {
        when (lessonBannerState) {
            is LessonBannerState.Before -> LessonBeforeContent(
                lessonBannerState = lessonBannerState,
                beforeLessonText = beforeLessonText ?: "",
            )

            is LessonBannerState.Ongoing -> LessonOngoingContent(lessonBannerState)
            is LessonBannerState.Completed -> LessonCompletedContent(lessonBannerState)
            is LessonBannerState.Canceled -> LessonCanceledContent()
        }
    }
}


@Composable
private fun LessonBeforeContent(
    lessonBannerState: LessonBannerState.Before,
    beforeLessonText: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
    ) {
        Text(
            text = beforeLessonText,
            color = SSINGTheme.colors.textNormal,
            style = SSINGTheme.typography.body.sb20,
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "강습생과 강사가 모두 강습 시작을 선택하면\n강습중 상태로 변경돼요",
            color = SSINGTheme.colors.textAlternative,
            style = SSINGTheme.typography.caption.md14,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max),
            horizontalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.End),
            verticalAlignment = Alignment.Bottom,
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                Text(
                    text = "${lessonBannerState.totalReadyCount}",
                    color = SSINGTheme.colors.primaryNormal,
                    style = SSINGTheme.typography.caption.sb12,
                )

                Text(
                    text = "/",
                    color = SSINGTheme.colors.primaryNormal,
                    style = SSINGTheme.typography.caption.sb12,
                )

                Text(
                    text = "${lessonBannerState.totalCount}",
                    color = SSINGTheme.colors.primaryNormal,
                    style = SSINGTheme.typography.caption.sb12,
                )
            }

            Icon(
                painter = painterResource(
                    id = if (lessonBannerState.isInstructorReady) {
                        R.drawable.img_instructor_ready
                    } else {
                        R.drawable.img_instructor_default
                    }
                ),
                contentDescription = null,
                tint = Color.Unspecified,
            )

            VerticalDivider(
                thickness = 2.dp,
                color = Blue100,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(
                        vertical = 4.dp
                    )
                    .clip(CircleShape),
            )

            repeat(lessonBannerState.participantTotalCount) { index ->
                val isReady = index < lessonBannerState.participantReadyCount

                Icon(
                    painter = painterResource(
                        id = if (isReady) R.drawable.img_waiting_ready else R.drawable.img_waiting_default,
                    ),
                    contentDescription = null,
                    tint = Color.Unspecified,
                )
            }
        }
    }
}

@Composable
private fun LessonOngoingContent(
    lessonBannerState: LessonBannerState.Ongoing,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(
                vertical = 16.dp,
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text(
                    text = "남은 시간",
                    color = SSINGTheme.colors.textNormal,
                    style = SSINGTheme.typography.body.sb16,
                )

                Text(
                    text = lessonBannerState.remainingTime,
                    color = SSINGTheme.colors.textNormal,
                    style = SSINGTheme.typography.title.sb32,
                )

                Text(
                    text = "강습 시작 후 ${lessonBannerState.elapsedTime} 경과",
                    color = SSINGTheme.colors.textAlternative,
                    style = SSINGTheme.typography.caption.md14,
                )
            }

            Icon(
                painter = painterResource(R.drawable.img_clock),
                contentDescription = null,
                tint = Color.Unspecified,
            )
        }
    }
}

@Composable
private fun LessonCompletedContent(
    lessonBannerState: LessonBannerState.Completed,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = 16.dp,
            ),
    ) {
        Icon(
            painter = painterResource(R.drawable.img_lesson_end),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .align(Alignment.BottomEnd),
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "강습이 종료됐어요",
                color = SSINGTheme.colors.textNormal,
                style = SSINGTheme.typography.body.sb20,
            )

            Text(
                text = lessonBannerState.lessonDate,
                color = SSINGTheme.colors.textAlternative,
                style = SSINGTheme.typography.caption.md14,
            )
        }
    }
}

@Composable
private fun LessonCanceledContent(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = 16.dp,
                bottom = 29.dp,
            ),
    ) {
        Icon(
            painter = painterResource(R.drawable.img_lesson_cancel),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .align(Alignment.BottomEnd),
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "강습이 취소됐어요",
                color = SSINGTheme.colors.textNormal,
                style = SSINGTheme.typography.body.sb20,
            )

            Text(
                text = "환불 상태를 확인해주세요.\n결제수단에 따라 최대 3일 걸릴 수 있어요.",
                color = SSINGTheme.colors.textAlternative,
                style = SSINGTheme.typography.caption.md14,
            )
        }
    }
}

private class LessonBannerPreviewProvider : PreviewParameterProvider<LessonBannerState> {
    override val values: Sequence<LessonBannerState>
        get() = sequenceOf(
            LessonBannerState.Before(
                isInstructorReady = true,
                participantReadyCount = 3,
                participantTotalCount = 5,
            ),
            LessonBannerState.Ongoing(
                remainingTime = "2:59:59",
                elapsedTime = "59분",
            ),
            LessonBannerState.Completed(
                lessonDate = "2026년 12월 31일",
            ),
            LessonBannerState.Canceled,
        )
}

@Preview(showBackground = true)
@Composable
private fun LessonBannerPreview(
    @PreviewParameter(LessonBannerPreviewProvider::class) lessonBannerState: LessonBannerState
) {
    SSINGTheme {
        LessonBanner(
            lessonBannerState = lessonBannerState,
            beforeLessonText = "강사님과 만난 후\n강습 시작을 눌러주세요",
        )
    }
}