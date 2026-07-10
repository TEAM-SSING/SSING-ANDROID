package com.ssing.presentation.consumerlesson.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.common.component.ConsumerInfoCard
import com.ssing.presentation.consumerlesson.model.ParticipantTeamUiModel
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun ParticipantTeamsSection(
    participantTeams: ImmutableList<ParticipantTeamUiModel>,
    modifier: Modifier = Modifier,
) {
    participantTeams.let { info ->
        ContentSection(
            titleText = "강습생 정보",
            modifier = modifier,
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                info.forEach { team ->
                    ConsumerInfoCard(
                        isReady = team.isReady,
                        nickname = team.nickname,
                        participants = team.participants,
                    )
                }
            }
        }
    }
}