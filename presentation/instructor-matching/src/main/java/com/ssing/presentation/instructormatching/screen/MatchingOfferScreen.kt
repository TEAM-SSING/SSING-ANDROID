package com.ssing.presentation.instructormatching.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
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
internal fun MatchingOfferScreen(
    offer: MatchingOfferUiModel,
    onBackClick: () -> Unit,
    onRejectOfferClick: () -> Unit,
    onAcceptOfferClick: () -> Unit,
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
            title = "새 강습이 도착했어요",
            subText = "조건에 맞는 강습 요청이에요\n수락하면 소비자에게 최종 확인 요청을 보내요",
            modifier = Modifier.padding(top = 24.dp),
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            SsingMatchingDetailCard(
                stepLabel = "빠른 요청",
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

            Text(
                text = "*거절하면 소비자에게 별도 안내 없이 다음 요청 탐색을 계속해요",
                style = SSINGTheme.typography.caption.sb12,
                color = SSINGTheme.colors.textAlternative,
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            SsingButton(
                text = "거절",
                onClick = onRejectOfferClick,
                style = SsingButtonStyle.RED,
                modifier = Modifier.weight(1f),
            )
            SsingButton(
                text = "수락",
                onClick = onAcceptOfferClick,
                style = SsingButtonStyle.BLUE,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MatchingOfferScreenPreview() {
    SSINGTheme {
        MatchingOfferScreen(
            offer = MatchingOfferUiModel(
                offerId = 21L,
                groupId = 3L,
                status = OfferStatusOption.OFFERED,
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
            onRejectOfferClick = {},
            onAcceptOfferClick = {},
        )
    }
}
