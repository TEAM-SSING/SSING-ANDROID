package com.ssing.presentation.consumerlesson.component.content

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.common.component.SsingMatchingDetailCardSmall
import com.ssing.presentation.consumerlesson.ConsumerLessonContract
import com.ssing.presentation.consumerlesson.component.ContentBackground
import com.ssing.presentation.consumerlesson.component.ContentSection
import com.ssing.presentation.consumerlesson.component.InstructorProfileSection
import com.ssing.presentation.consumerlesson.component.LessonActionButton
import com.ssing.presentation.consumerlesson.component.LessonManagementSection

@Composable
internal fun CompletedLessonContent(
    state: ConsumerLessonContract.State,
    onReportIssueClick: () -> Unit,
    onAdditionalLessonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ContentBackground(
        modifier = modifier,
    ) {
        state.completedLessonInfo?.let { info ->
            ContentSection(
                titleText = "강습 정보",
            ) {
                SsingMatchingDetailCardSmall(
                    tags = info.lessonInfo.tags,
                    teamNicknames = info.lessonInfo.teamNicknames,
                    totalCount = info.lessonInfo.totalCount,
                    place = info.lessonInfo.place,
                    duration = info.lessonInfo.duration,
                    actualTimeRange = info.actualTimeRange,
                    price = info.lessonInfo.price,
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        InstructorProfileSection(state.instructorProfile)

        Spacer(modifier = Modifier.height(24.dp))

        LessonManagementSection(
            primaryButton = LessonActionButton("문제 신고", onReportIssueClick),
            secondaryButton = LessonActionButton("이 강사님 추가 예약", onAdditionalLessonClick)
        )
    }
}