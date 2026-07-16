package com.ssing.presentation.consumerlesson.mapper

import com.ssing.core.ui.type.displayLessonLevel
import com.ssing.core.ui.type.displaySport
import com.ssing.core.ui.type.formatMinutesText
import com.ssing.data.lesson.common.model.MatchingRequestCommon
import com.ssing.data.lesson.consumer.model.ConsumerLessonInfo
import com.ssing.presentation.consumerlesson.model.LessonInfoUiModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

internal fun ConsumerLessonInfo.toUiModel(
    durationMinutes: Int,
    matchingRequests: List<MatchingRequestCommon>,
): LessonInfoUiModel = LessonInfoUiModel(
    tags = persistentListOf(displaySport(basic.sport), displayLessonLevel(basic.lessonLevel)),
    teamNicknames = matchingRequests
        .map { it.representativeMemberName }
        .toPersistentList(),
    totalCount = basic.totalHeadcount,
    place = basic.resort.displayName,
    duration = formatMinutesText(durationMinutes),
    price = myTeamLessonPrice,
)