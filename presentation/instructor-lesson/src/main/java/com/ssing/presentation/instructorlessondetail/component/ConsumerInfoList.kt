package com.ssing.presentation.instructorlessondetail.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.common.component.ConsumerInfoCard
import com.ssing.presentation.instructorlessondetail.model.TeamParticipantsInfo
import kotlinx.collections.immutable.ImmutableList

@Composable
fun ConsumerInfoList(
    teams: ImmutableList<TeamParticipantsInfo>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        teams.forEachIndexed { index, team ->
            ConsumerInfoCard(
                isReady = team.isReady ?: false,
                nickname = team.teamNickname,
                participants = team.participants,
                price = team.price,
            )
            if (index != teams.lastIndex) {
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}