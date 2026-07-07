package com.ssing.presentation.instructormatching.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.common.component.SsingButton
import com.ssing.core.ui.common.component.SsingButtonStyle
import com.ssing.core.ui.common.component.SsingHeader
import com.ssing.core.ui.common.component.SsingTopBar
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.presentation.instructormatching.model.LessonSummaryUiModel
import com.ssing.presentation.instructormatching.model.MatchingOfferUiModel
import com.ssing.presentation.instructormatching.model.OfferStatusOption

@Composable
internal fun MatchingPendingScreen(
    offer: MatchingOfferUiModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SSINGTheme.colors.backgroundNormal),
    ) {
        SsingTopBar(
            title = "강습 확정 대기",
            onBack = onBackClick,
        )
        SsingHeader(
            title = "강습생의 확정을 기다리고 있어요",
            subText = "강습생에게 최종 확인 요청을 보냈어요\n강습생이 확인하면 강습이 확정돼요",
            modifier = Modifier.padding(top = 24.dp),
        )

        // TODO(매칭-그래픽): 대기 그래픽 에셋 확정 시 추가

        Spacer(modifier = Modifier.weight(1f))

        //TODO 강습 상세 정보 카드 예지꺼 머지후 추가

        SsingButton(
            text = "대기중",
            onClick = {},
            style = SsingButtonStyle.GRAY,
            enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MatchingPendingScreenPreview() {
    SSINGTheme {
        MatchingPendingScreen(
            offer = MatchingOfferUiModel(
                offerId = 21L,
                groupId = 3L,
                status = OfferStatusOption.ACCEPTED,
                expiresAtMillis = null,
                lesson = LessonSummaryUiModel(
                    resortLabel = "하이원 리조트",
                    sportLabel = "보드",
                    levelLabel = "처음 타요",
                    headcount = 4,
                    durationHours = 2,
                ),
            ),
            onBackClick = {},
        )
    }
}