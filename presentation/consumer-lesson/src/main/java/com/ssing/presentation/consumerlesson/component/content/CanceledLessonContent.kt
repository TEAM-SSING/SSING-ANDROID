package com.ssing.presentation.consumerlesson.component.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ssing.presentation.consumerlesson.ConsumerLessonContract
import com.ssing.presentation.consumerlesson.component.InstructorProfileSection
import com.ssing.presentation.consumerlesson.component.LessonInfoSection
import com.ssing.presentation.consumerlesson.component.LessonManagementSection

@Composable
internal fun CanceledLessonContent(
    state: ConsumerLessonContract.State,
    onReportIssueClick: () -> Unit,
    onLessonListClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        LessonInfoSection(
            lessonInfo = state.canceledLessonInfo?.lessonInfo,
            cancelDateTime = state.canceledLessonInfo?.cancelDateTime ?: "",
            cancelSubject = state.canceledLessonInfo?.cancelSubject ?: "",
            cancelReason = state.canceledLessonInfo?.cancelReason ?: "",
        )

        Spacer(modifier = Modifier.height(12.dp))

        InstructorProfileSection(state.instructorProfile)

        Spacer(modifier = Modifier.height(24.dp))

        LessonManagementSection(
            leftButtonText = "문제 신고",
            leftButtonClick = onReportIssueClick,
            rightButtonText = "강습 내역 보기",
            rightButtonClick = onLessonListClick,
        )
    }
}