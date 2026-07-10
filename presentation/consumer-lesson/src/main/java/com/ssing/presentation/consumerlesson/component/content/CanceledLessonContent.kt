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
internal fun CanceledLessonContent(
    state: ConsumerLessonContract.State,
    onReportIssueClick: () -> Unit,
    onLessonListClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ContentBackground(
        modifier = modifier,
    ) {
        state.canceledLessonInfo?.let { info ->
            ContentSection(
                titleText = "강습 정보",
            ) {
                SsingMatchingDetailCardSmall(
                    tags = info.lessonInfo.tags,
                    teamNicknames = info.lessonInfo.teamNicknames,
                    totalCount = info.lessonInfo.totalCount,
                    place = info.lessonInfo.place,
                    duration = info.lessonInfo.duration,
                    price = info.lessonInfo.price,
                    cancelDateTime = info.cancelDateTime,
                    cancelSubject = info.cancelSubject,
                    cancelReason = info.cancelReason,
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        InstructorProfileSection(state.instructorProfile)

        Spacer(modifier = Modifier.height(24.dp))

        LessonManagementSection(
            primaryButton = LessonActionButton("문제 신고", onReportIssueClick),
            secondaryButton = LessonActionButton("강습 내역 보기", onLessonListClick)
        )
    }
}