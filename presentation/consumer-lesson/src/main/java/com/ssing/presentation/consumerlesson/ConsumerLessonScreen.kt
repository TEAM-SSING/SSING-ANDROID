package com.ssing.presentation.consumerlesson

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssing.core.ui.common.component.LessonBanner
import com.ssing.core.ui.common.component.LessonBannerState
import com.ssing.core.ui.common.component.SsingButton
import com.ssing.core.ui.common.component.SsingButtonStyle
import com.ssing.core.ui.common.component.SsingTopBar
import com.ssing.core.ui.designsystem.theme.Blue50
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.core.ui.designsystem.theme.White

@Composable
internal fun ConsumerLessonRoute(
    modifier: Modifier = Modifier,
    viewModel: ConsumerLessonViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ConsumerLessonScreen(
        state = state,
        modifier = modifier,
    )
}

@Composable
private fun ConsumerLessonScreen(
    state: ConsumerLessonContract.State,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(White)
    ) {
        SsingTopBar(
            title = "강습 상세",
            onBack = {
                // TODO: 홈으로 이동
            },
            backgroundColor = Blue50,
        )

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            item {
                LessonBanner(
                    lessonBannerState = state.lessonBannerState,
                    beforeLessonText = "강사님과 만난 후\n강습시작을 눌러주세요",
                )
            }

            item {
                when (val bannerState = state.lessonBannerState) {
                    is LessonBannerState.Before -> BeforeLessonBody(bannerState)
                    is LessonBannerState.Ongoing -> OngoingLessonBody(bannerState)
                    is LessonBannerState.Completed -> CompletedLessonBody(bannerState)
                    is LessonBannerState.Canceled -> CanceledLessonBody(bannerState)
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp,
                ),
            contentAlignment = Alignment.BottomCenter,
        ) {
            BottomButton(
                state = state,
                onClick = {
                    // TODO: LessonState 단계별 내비게이션
                },
            )
        }

    }
}

@Composable
private fun BeforeLessonBody(
    state: LessonBannerState.Before,
    modifier: Modifier = Modifier,
) {
    BodyBackground(
        modifier = modifier,
    ) {

    }
}

@Composable
private fun OngoingLessonBody(
    state: LessonBannerState.Ongoing,
    modifier: Modifier = Modifier,
) {

}

@Composable
private fun CompletedLessonBody(
    state: LessonBannerState.Completed,
    modifier: Modifier = Modifier,
) {

}

@Composable
private fun CanceledLessonBody(
    state: LessonBannerState.Canceled,
    modifier: Modifier = Modifier,
) {

}

@Composable
private fun BodyBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .background(Blue50),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = SSINGTheme.colors.backgroundNormal,
                    shape = RoundedCornerShape(
                        topStart = 12.dp,
                        topEnd = 12.dp,
                    )
                )
                .padding(16.dp),
        ) {
            content()
        }
    }
}

@Composable
private fun BodySection(
    titleText: String,
    modifier: Modifier = Modifier,
    spacer: Int = 8,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(spacer.dp),
    ) {
        Text(
            text = titleText,
            color = SSINGTheme.colors.textAlternative,
            style = SSINGTheme.typography.caption.sb12,
        )

        content()
    }
}

@Composable
private fun BottomButton(
    state: ConsumerLessonContract.State,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val buttonText = when (state.lessonBannerState) {
        is LessonBannerState.Before -> if (state.isReady) {
            "강습 대기중"
        } else {
            "강습 준비 완료"
        }
        is LessonBannerState.Ongoing -> "강습 종료"
        is LessonBannerState.Completed -> "리뷰 쓰기"
        is LessonBannerState.Canceled -> "홈으로 돌아가기"
    }

    SsingButton(
        text = buttonText,
        onClick = onClick,
        style = SsingButtonStyle.BLUE,
        modifier = Modifier.fillMaxWidth(),
    )
}

@Preview(showBackground = true)
@Composable
private fun ConsumerLessonScreenPreview() {
    SSINGTheme {
        ConsumerLessonScreen(
            state = ConsumerLessonContract.State(
                lessonBannerState = LessonBannerState.Before(
                    isInstructorReady = false,
                    participantReadyCount = 2,
                    participantTotalCount = 5,
                )
            ),
        )
    }
}