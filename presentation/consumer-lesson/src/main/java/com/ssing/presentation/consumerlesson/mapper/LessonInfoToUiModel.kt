package com.ssing.presentation.consumerlesson.mapper

import com.ssing.data.consumerlesson.model.LessonInfo
import com.ssing.data.consumerlesson.model.LessonMatchingRequest
import com.ssing.presentation.consumerlesson.model.LessonInfoUiModel
import com.ssing.presentation.consumerlesson.util.displayLessonLevel
import com.ssing.presentation.consumerlesson.util.displaySport
import com.ssing.presentation.consumerlesson.util.formatMinutesText
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

internal fun LessonInfo.toUiModel(
    durationMinutes: Int,
    matchingRequests: List<LessonMatchingRequest>,
): LessonInfoUiModel = LessonInfoUiModel(
    tags = persistentListOf(displaySport(sport), displayLessonLevel(lessonLevel)),
    teamNicknames = matchingRequests
        .map { "${it.representativeMemberName}님 팀" }
        .toPersistentList(),
    totalCount = totalHeadcount,
    place = resortDisplayName,
    duration = formatMinutesText(durationMinutes),
    price = myTeamLessonPrice,
)