package com.ssing.presentation.consumerlesson.mapper

import com.ssing.core.ui.type.displayLessonLevel
import com.ssing.core.ui.type.displaySport
import com.ssing.core.ui.type.formatMinutesText
import com.ssing.data.consumerlesson.model.LessonInfo
import com.ssing.data.consumerlesson.model.LessonMatchingRequest
import com.ssing.presentation.consumerlesson.model.LessonInfoUiModel
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