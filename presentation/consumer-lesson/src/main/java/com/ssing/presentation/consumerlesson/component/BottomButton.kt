package com.ssing.presentation.consumerlesson.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ssing.core.ui.common.component.LessonBannerState
import com.ssing.core.ui.common.component.SsingButton
import com.ssing.core.ui.common.component.SsingButtonStyle
import com.ssing.presentation.consumerlesson.ConsumerLessonContract

@Composable
internal fun BottomButton(
    state: ConsumerLessonContract.State,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isBeforeAndReady = state.lessonBannerState is LessonBannerState.Before && state.isReady

    val buttonText = when (state.lessonBannerState) {
        is LessonBannerState.Before -> if (state.isReady) {
            "강습 준비 완료"
        } else {
            "강습 대기 중"
        }

        is LessonBannerState.Ongoing -> "강습 종료"
        is LessonBannerState.Completed -> "리뷰 쓰기"
        is LessonBannerState.Canceled -> "홈으로 돌아가기"
    }

    SsingButton(
        text = buttonText,
        onClick = onClick,
        style = if (isBeforeAndReady) SsingButtonStyle.GRAY else SsingButtonStyle.BLUE,
        modifier = modifier.fillMaxWidth(),
        enabled = !isBeforeAndReady,
    )
}