package com.ssing.presentation.consumermatching.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.common.component.SsingButton
import com.ssing.core.ui.common.component.SsingButtonStyle
import com.ssing.core.ui.common.component.SsingHeader
import com.ssing.core.ui.common.component.SsingMatchingDetailCard
import com.ssing.core.ui.common.component.SsingTopBar
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

// TODO: #91 병합되면 삭제 -> ConsumerMatchingContract.State 내부로 이동
data class ConsumerMatchingPendingUiState(
    val tags: ImmutableList<String> = persistentListOf(),
    val nickname: String = "",
    val teamCount: Int = 0,
    val location: String = "",
    val duration: String = "",
    val price: Int = 0,
) {
    val detailCardTitle: String =
        "${nickname}님" + if (teamCount > 2) {
            "외 ${teamCount - 1}명"
        } else ""
}

@Composable
fun ConsumerMatchingPendingScreen(
    // TODO : #91 병합되면 ConsumerMatchingContract.State로 변경
    state: ConsumerMatchingPendingUiState,
    onEditClick: () -> Unit,
    onStopClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            SsingTopBar(
                title = "씽 매칭중",
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .background(SSINGTheme.colors.backgroundNormal)
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                SsingButton(
                    text = "조건 수정",
                    onClick = onEditClick,
                    style = SsingButtonStyle.GRAY,
                    modifier = Modifier.weight(1f),
                )

                SsingButton(
                    text = "대기 중지",
                    onClick = onStopClick,
                    style = SsingButtonStyle.BLUE,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SsingHeader(
                title = "조건에 맞는 강사님을 찾고있어요",
                subText = "요청 조건에 맞는 강사님을 확인하고 있어요",
                modifier = Modifier.padding(vertical = 16.dp),
            )

            Box(
                modifier = Modifier
                    .size(217.dp)
                    .background(SSINGTheme.colors.primaryAlternative),
            )

            SsingMatchingDetailCard(
                modifier = Modifier.padding(horizontal = 16.dp),
                stepLabel = "현재 매칭 조건",
                stepLabelColor = SSINGTheme.colors.textAlternative,
                tags = state.tags,
                title = state.detailCardTitle,
                location = state.location,
                duration = state.duration,
                price = state.price,
                equipmentStatus = "착용 완료",
                // TODO: #66 병합되면 borderColor borderAlternative 적용
            )
        }
    }
}

private class ConsumerMatchingPendingScreenPreviewProvider : PreviewParameterProvider<Int> {
    override val values: Sequence<Int>
        get() = sequenceOf(1, 5)
}

@Preview
@Composable
private fun ConsumerMatchingPendingScreenPreview(
    @PreviewParameter(ConsumerMatchingPendingScreenPreviewProvider::class) teamCount: Int,
) {
    SSINGTheme {
        ConsumerMatchingPendingScreen(
            state = ConsumerMatchingPendingUiState(
                tags = persistentListOf("스노보드", "처음타요"),
                nickname = "홍지민",
                teamCount = teamCount,
                location = "OOO 리조트",
                duration = "2시간",
                price = 87500
            ),
            onEditClick = {},
            onStopClick = {},
        )
    }
}
