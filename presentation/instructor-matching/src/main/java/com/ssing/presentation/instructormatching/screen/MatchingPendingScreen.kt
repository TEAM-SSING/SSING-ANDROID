package com.ssing.presentation.instructormatching.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.common.component.SsingButton
import com.ssing.core.ui.common.component.SsingButtonStyle
import com.ssing.core.ui.common.component.SsingHeader
import com.ssing.core.ui.common.component.SsingMatchingDetailCard
import com.ssing.core.ui.common.component.SsingTopBar
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.presentation.instructormatching.model.LessonSummaryUiModel
import com.ssing.presentation.instructormatching.model.MatchingOfferUiModel
import com.ssing.presentation.instructormatching.model.OfferStatusOption
import com.ssing.presentation.instructormatching.model.ParticipantUiModel
import com.ssing.presentation.instructormatching.model.toParticipant
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

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

        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            SsingMatchingDetailCard(
                stepLabel = "수락 완료",
                stepLabelColor = SSINGTheme.colors.primaryNormal,
                tags = persistentListOf(offer.lesson.sportLabel, offer.lesson.levelLabel),
                nickname = offer.nickname,
                teamCount = offer.teamCount,
                totalCount = offer.lesson.headcount,
                classDateTime = offer.classDateTime,
                location = offer.lesson.resortLabel,
                duration = "${offer.lesson.durationHours}시간",
                participants = offer.participants.map { it.toParticipant() }.toPersistentList(),
                price = offer.price,
            )

            Spacer(modifier = Modifier.height(23.dp))
            
            SsingButton(
                text = "대기중",
                onClick = {},
                style = SsingButtonStyle.GRAY,
                enabled = false,
                modifier = Modifier.fillMaxWidth()
            )
        }
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
                nickname = "홍지민",
                teamCount = 4,
                classDateTime = "7월 8일 수요일 오후 12:30",
                participants = listOf(
                    ParticipantUiModel(age = 11, isMale = true),
                    ParticipantUiModel(age = 11, isMale = true),
                    ParticipantUiModel(age = 9, isMale = false),
                ),
                price = 87500,
                equipmentStatus = "착용 완료",
                lesson = LessonSummaryUiModel(
                    resortLabel = "하이원 리조트",
                    sportLabel = "스노보드",
                    levelLabel = "처음타요",
                    headcount = 4,
                    durationHours = 2,
                ),
            ),
            onBackClick = {},
        )
    }
}
