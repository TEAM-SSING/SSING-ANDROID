package com.ssing.presentation.instructormatching.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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

@Composable
internal fun MatchingWaitingScreen(
    onBackClick: () -> Unit,
    onEditConditionClick: () -> Unit,
    onStopWaitingClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SSINGTheme.colors.backgroundNormal),
    ) {
        SsingTopBar(
            title = "씽 매칭중",
            onBack = onBackClick,
        )

        SsingHeader(
            title = "조건에 맞는 강습요청을 찾고 있어요",
            subText = "조건에 맞는 강습요청이 들어오면 바로 확인할 수 있어요",
            modifier = Modifier.padding(top = 24.dp),
        )

        // TODO(매칭-그래픽): 로딩 그래픽 에셋 확정 시 이 위치에 추가

        //TODO 강습상세 정보 카드 예지꺼 머지 되면 추가

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            SsingButton(
                text = "조건 수정",
                onClick = onEditConditionClick,
                style = SsingButtonStyle.GRAY,
                modifier = Modifier.weight(1f),
            )
            SsingButton(
                text = "대기 중지",
                onClick = onStopWaitingClick,
                style = SsingButtonStyle.BLUE,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MatchingWaitingScreenPreview() {
    SSINGTheme {
        MatchingWaitingScreen(
            onBackClick = {},
            onEditConditionClick = {},
            onStopWaitingClick = {},
        )
    }
}