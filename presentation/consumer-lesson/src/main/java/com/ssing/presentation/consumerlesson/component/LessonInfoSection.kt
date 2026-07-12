package com.ssing.presentation.consumerlesson.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ssing.core.ui.common.component.SsingMatchingDetailCardSmall
import com.ssing.presentation.consumerlesson.model.LessonInfoUiModel

@Composable
internal fun LessonInfoSection(
    lessonInfo: LessonInfoUiModel?,
    modifier: Modifier = Modifier,
    cancelDateTime: String = "",
    cancelSubject: String = "",
    cancelReason: String = "",
    actualTimeRange: String = "",
) {
    lessonInfo?.let { info ->
        ContentSection(
            titleText = "강습 정보",
            modifier = modifier,
        ) {
            SsingMatchingDetailCardSmall(
                tags = info.tags,
                teamNicknames = info.teamNicknames,
                totalCount = info.totalCount,
                place = info.place,
                duration = info.duration,
                actualTimeRange = actualTimeRange,
                price = info.price,
                cancelDateTime = cancelDateTime,
                cancelSubject = cancelSubject,
                cancelReason = cancelReason,
            )
        }
    }
}