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
import com.ssing.presentation.consumerlesson.component.ParticipantTeamsSection

@Composable
internal fun OngoingLessonContent(
    state: ConsumerLessonContract.State,
    onReportIssueClick: () -> Unit,
    onChatClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        LessonInfoSection(state.lessonInfo)

        Spacer(modifier = Modifier.height(12.dp))

        InstructorProfileSection(state.instructorProfile)

        Spacer(modifier = Modifier.height(24.dp))

        ParticipantTeamsSection(state.participantTeams)

        Spacer(modifier = Modifier.height(24.dp))

        LessonManagementSection(
            leftButtonText = "문제 신고",
            leftButtonClick = onReportIssueClick,
            rightButtonText = "채팅방",
            rightButtonClick = onChatClick,
        )
    }
}