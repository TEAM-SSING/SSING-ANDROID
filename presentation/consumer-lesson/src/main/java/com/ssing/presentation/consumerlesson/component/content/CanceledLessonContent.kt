package com.ssing.presentation.consumerlesson.component.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.presentation.consumerlesson.ConsumerLessonContract
import com.ssing.presentation.consumerlesson.component.InstructorProfileSection
import com.ssing.presentation.consumerlesson.component.LessonInfoSection
import com.ssing.presentation.consumerlesson.component.LessonManagementSection
import com.ssing.presentation.consumerlesson.model.CanceledLessonInfoUiModel
import com.ssing.presentation.consumerlesson.model.InstructorProfileUiModel
import com.ssing.presentation.consumerlesson.model.LessonInfoUiModel
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun CanceledLessonContent(
    state: ConsumerLessonContract.State,
    onInstructorProfileClick: () -> Unit,
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

        InstructorProfileSection(
            state.instructorProfile,
            onInstructorProfileClick,
        )

        Spacer(modifier = Modifier.height(24.dp))

        LessonManagementSection(
            leftButtonText = "문제 신고",
            onLeftClick = onReportIssueClick,
            rightButtonText = "강습 내역 보기",
            onRightClick = onLessonListClick,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CanceledLessonContentPreview() {
    SSINGTheme {
        val sampleLessonInfo = LessonInfoUiModel(
            tags = persistentListOf("스노보드", "자격증이 있어요"),
            teamNicknames = persistentListOf("김멍멍", "김야옹"),
            totalCount = 2,
            place = "000 리조트",
            duration = "2시간",
            price = 500000,
        )

        val state = ConsumerLessonContract.State(
            canceledLessonInfo = CanceledLessonInfoUiModel(
                lessonInfo = sampleLessonInfo,
                cancelDateTime = "2026.07.10 14:00",
                cancelSubject = "강습생",
                cancelReason = "일정 변경",
            ),
            instructorProfile = InstructorProfileUiModel(
                name = "김어흥 강사",
                age = 27,
                gender = "남",
                level = "grade1",
                imageUrl = "",
            ),
        )

        CanceledLessonContent(
            state = state,
            onInstructorProfileClick = {},
            onReportIssueClick = {},
            onLessonListClick = {},
        )
    }
}