package com.ssing.presentation.instructormatching.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ssing.core.ui.common.component.SsingMatchingDetailCard
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.presentation.instructormatching.model.MatchingOfferUiModel
import com.ssing.presentation.instructormatching.model.toParticipant
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

@Composable
internal fun MatchingOfferSummaryDetailCard(
    offer: MatchingOfferUiModel,
    stepLabel: String,
    modifier: Modifier = Modifier,
    stepLabelColor: Color = SSINGTheme.colors.primaryNormal,
) {
    SsingMatchingDetailCard(
        stepLabel = stepLabel,
        stepLabelColor = stepLabelColor,
        tags = persistentListOf(offer.lesson.sportLabel, offer.lesson.levelLabel),
        nickname = offer.nickname,
        teamCount = offer.teamCount,
        totalCount = offer.lesson.headcount,
        classDateTime = offer.classDateTime,
        location = offer.lesson.resortLabel,
        duration = "${offer.lesson.durationHours}시간",
        participants = offer.participants.map { it.toParticipant() }.toPersistentList(),
        price = offer.price,
        modifier = modifier,
    )
}
