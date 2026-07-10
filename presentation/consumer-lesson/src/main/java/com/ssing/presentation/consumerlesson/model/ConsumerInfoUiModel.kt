package com.ssing.presentation.consumerlesson.model

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class ParticipantTeamUiModel(
    val isReady: Boolean,
    val nickname: String,
    val participants: ImmutableList<String>,
)