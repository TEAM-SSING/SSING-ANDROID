package com.ssing.presentation.consumerlesson.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.common.component.ConsumerInfoCard
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.presentation.consumerlesson.model.ParticipantTeamUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun ParticipantTeamsSection(
    participantTeams: ImmutableList<ParticipantTeamUiModel>,
    modifier: Modifier = Modifier,
) {
    if (participantTeams.isNotEmpty()) {
        ContentSection(
            titleText = "강습생 정보",
            modifier = modifier,
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                participantTeams.forEach { team ->
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

@Preview(showBackground = true)
@Composable
private fun ParticipantTeamsSectionPreview() {
    SSINGTheme {
        ParticipantTeamsSection(
            participantTeams = persistentListOf(
                ParticipantTeamUiModel(
                    isReady = true,
                    nickname = "김음메",
                    participants = persistentListOf("38세 남", "12세 여", "9세 남"),
                ),
                ParticipantTeamUiModel(
                    isReady = false,
                    nickname = "김끼룩",
                    participants = persistentListOf("38세 남", "12세 여", "9세 남"),
                ),
            ),
        )
    }
}