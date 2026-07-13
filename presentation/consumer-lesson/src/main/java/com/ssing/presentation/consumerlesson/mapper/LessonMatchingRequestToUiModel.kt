package com.ssing.presentation.consumerlesson.mapper

import com.ssing.data.consumerlesson.model.LessonMatchingRequest
import com.ssing.presentation.consumerlesson.model.ParticipantTeamUiModel
import com.ssing.presentation.consumerlesson.util.displayGender
import kotlinx.collections.immutable.toPersistentList

internal fun LessonMatchingRequest.toUiModel(): ParticipantTeamUiModel = ParticipantTeamUiModel(
    isReady = startConfirmed,
    nickname = representativeMemberName,
    participants = participants
        .map { "${it.age}세 ${displayGender(it.gender)}" }
        .toPersistentList(),
)