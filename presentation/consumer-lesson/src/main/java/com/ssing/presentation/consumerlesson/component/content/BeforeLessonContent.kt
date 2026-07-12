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
import com.ssing.presentation.consumerlesson.component.ParticipantTeamsSection
import com.ssing.presentation.consumerlesson.model.InstructorProfileUiModel
import com.ssing.presentation.consumerlesson.model.LessonInfoUiModel
import com.ssing.presentation.consumerlesson.model.ParticipantTeamUiModel
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun BeforeLessonContent(
    state: ConsumerLessonContract.State,
    onCancelClick: () -> Unit,
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
            leftButtonText = "강습 취소",
            onLeftClick = onCancelClick,
            rightButtonText = "채팅방",
            onRightClick = onChatClick,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BeforeLessonContentPreview() {
    SSINGTheme {
        val state = ConsumerLessonContract.State(
            lessonInfo = LessonInfoUiModel(
                tags = persistentListOf("스노보드", "자격증이 있어요"),
                teamNicknames = persistentListOf("김멍멍", "김야옹"),
                totalCount = 2,
                place = "000 리조트",
                duration = "2시간",
                price = 500000,
            ),
            instructorProfile = InstructorProfileUiModel(
                name = "김어흥 강사",
                age = 27,
                gender = "남",
                level = "grade1",
                imageUrl = "",
            ),
            participantTeams = persistentListOf(
                ParticipantTeamUiModel(
                    isReady = true,
                    nickname = "김음메",
                    participants = persistentListOf("38세 남", "12세 여", "9세 남"),
                ),
                ParticipantTeamUiModel(
                    isReady = false,
                    nickname = "김끼룩",
                    participants = persistentListOf("38세 남", "12세 여", "9세 남"),
                ),
            ),
        )

        BeforeLessonContent(
            state = state,
            onCancelClick = {},
            onChatClick = {},
        )
    }
}