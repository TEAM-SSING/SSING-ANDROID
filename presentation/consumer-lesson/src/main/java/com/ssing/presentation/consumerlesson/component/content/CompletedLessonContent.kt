package com.ssing.presentation.consumerlesson.component.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ssing.presentation.consumerlesson.ConsumerLessonContract
import com.ssing.presentation.consumerlesson.component.InstructorProfileSection
import com.ssing.presentation.consumerlesson.component.LessonActionButton
import com.ssing.presentation.consumerlesson.component.LessonInfoSection
import com.ssing.presentation.consumerlesson.component.LessonManagementSection

@Composable
internal fun CompletedLessonContent(
    state: ConsumerLessonContract.State,
    onReportIssueClick: () -> Unit,
    onAdditionalLessonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        LessonInfoSection(
            lessonInfo = state.completedLessonInfo?.lessonInfo,
            actualTimeRange = state.completedLessonInfo?.actualTimeRange ?: "",
        )

        Spacer(modifier = Modifier.height(12.dp))

        InstructorProfileSection(state.instructorProfile)

        Spacer(modifier = Modifier.height(24.dp))

        LessonManagementSection(
            leftButton = LessonActionButton("문제 신고", onReportIssueClick),
            rightButton = LessonActionButton("이 강사님 추가 예약", onAdditionalLessonClick)
        )
    }
}