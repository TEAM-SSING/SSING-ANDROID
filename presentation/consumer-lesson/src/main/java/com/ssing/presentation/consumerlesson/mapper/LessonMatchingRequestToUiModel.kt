package com.ssing.presentation.consumerlesson.mapper

import com.ssing.core.ui.type.displayGender
import com.ssing.data.lesson.consumer.model.ConsumerConfirmedMatchingRequest
import com.ssing.data.lesson.consumer.model.ConsumerMatchingRequest
import com.ssing.presentation.consumerlesson.model.ParticipantTeamUiModel
import kotlinx.collections.immutable.toPersistentList

internal fun ConsumerConfirmedMatchingRequest.toUiModel(): ParticipantTeamUiModel = ParticipantTeamUiModel(
    isReady = startConfirmed,
    nickname = representativeMemberName,
    participants = participants
        .map { "${it.age}세 ${displayGender(it.gender)}" }
        .toPersistentList(),
)

internal fun ConsumerMatchingRequest.toUiModel(): ParticipantTeamUiModel = ParticipantTeamUiModel(
    isReady = false,
    nickname = representativeMemberName,
    participants = participants
        .map { "${it.age}세 ${displayGender(it.gender)}" }
        .toPersistentList(),
)