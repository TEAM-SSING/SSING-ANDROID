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
import com.ssing.core.ui.common.component.Gender
import com.ssing.core.ui.common.component.Participant
import com.ssing.core.ui.common.component.SsingButton
import com.ssing.core.ui.common.component.SsingButtonStyle
import com.ssing.core.ui.common.component.SsingHeader
import com.ssing.core.ui.common.component.SsingMatchingDetailCard
import com.ssing.core.ui.common.component.SsingTopBar
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.presentation.instructormatching.model.ConditionUiState
import com.ssing.presentation.instructormatching.model.DurationOption
import com.ssing.presentation.instructormatching.model.LevelOption
import com.ssing.presentation.instructormatching.model.MatchingWaitingUiState
import com.ssing.presentation.instructormatching.model.ParticipantUiModel
import com.ssing.presentation.instructormatching.model.SportOption
import com.ssing.presentation.instructormatching.model.toParticipant
import kotlinx.collections.immutable.toPersistentList

@Composable
internal fun MatchingWaitingScreen(
    condition: ConditionUiState,
    waiting: MatchingWaitingUiState,
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

        SsingMatchingDetailCard(
            stepLabel = "씽 매칭 대기중",
            tags = (condition.selectedSports.map { it.label } + condition.selectedLevels.map { it.label })
                .toPersistentList(),
            nickname = waiting.nickname,
            teamCount = waiting.teamCount,
            totalCount = condition.maxHeadcount,
            classDateTime = waiting.classDateTime,
            location = condition.resortName,
            duration = condition.selectedDurations.firstOrNull()?.label ?: "",
            maxCapacity = condition.maxHeadcount,
            participants = waiting.participants.map { it.toParticipant() }.toPersistentList(),
            isPaid = waiting.isPaid,
            price = waiting.price,
            equipmentStatus = waiting.equipmentStatus,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
        )

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
            condition = ConditionUiState(
                resortName = "하이원 리조트",
                selectedSports = setOf(SportOption.SKI),
                selectedLevels = setOf(LevelOption.BEGINNER),
                selectedDurations = setOf(DurationOption.HOUR_3),
                maxHeadcount = 4,
            ),
            waiting = MatchingWaitingUiState(
                nickname = "김OO",
                teamCount = 2,
                classDateTime = "1월 7일 오전 10:00",
                participants = listOf(
                    ParticipantUiModel(age = 28, isMale = true),
                    ParticipantUiModel(age = 25, isMale = false),
                ),
                isPaid = true,
                price = 80000,
                equipmentStatus = "착용 완료",
            ),
            onBackClick = {},
            onEditConditionClick = {},
            onStopWaitingClick = {},
        )
    }
}
