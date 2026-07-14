package com.ssing.presentation.instructormatching.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ssing.core.ui.common.component.SsingButton
import com.ssing.core.ui.common.component.SsingButtonStyle
import com.ssing.core.ui.common.component.SsingHeader
import com.ssing.core.ui.common.component.SsingMatchingDetailCard
import com.ssing.core.ui.common.component.SsingTopBar
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.presentation.instructormatching.R
import com.ssing.presentation.instructormatching.model.DurationOption
import com.ssing.presentation.instructormatching.model.LevelOption
import com.ssing.presentation.instructormatching.model.MatchingExposureUiState
import com.ssing.presentation.instructormatching.model.MatchingWaitingUiState
import com.ssing.presentation.instructormatching.model.ParticipantUiModel
import com.ssing.presentation.instructormatching.model.SportOption
import com.ssing.presentation.instructormatching.model.toParticipant
import kotlinx.collections.immutable.toPersistentList

@Composable
internal fun MatchingWaitingScreen(
    exposure: MatchingExposureUiState,
    waiting: MatchingWaitingUiState,
    onBackClick: () -> Unit,
    onEditExposureClick: () -> Unit,
    onStopWaitingClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.instructor_matching_waiting))

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
    )
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
            modifier = Modifier.padding(vertical = 16.dp),
        )

        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier
                .padding(all = 16.dp)
                .fillMaxWidth()
                .widthIn(max = 328.dp)
                .heightIn(max = 167.dp)
        )

        SsingMatchingDetailCard(
            stepLabel = "현재 매칭 조건",
            tags = (listOfNotNull(exposure.selectedSports?.label) + exposure.selectedLevels.map { it.label })
                .toPersistentList(),
            totalCount = exposure.maxHeadcount,
            location = exposure.resortName,
            duration = exposure.selectedDurations.joinToString(" / ") { it.label },
            maxCapacity = exposure.maxHeadcount,
            participant = waiting.participant,
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
                onClick = onEditExposureClick,
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
            exposure = MatchingExposureUiState(
                resortName = "하이원 리조트",
                selectedSports = SportOption.SKI,
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
            onEditExposureClick = {},
            onStopWaitingClick = {},
        )
    }
}
