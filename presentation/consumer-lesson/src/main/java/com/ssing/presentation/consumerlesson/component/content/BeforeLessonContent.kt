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
import com.ssing.presentation.consumerlesson.component.ParticipantTeamsSection

@Composable
internal fun BeforeLessonContent(
    state: ConsumerLessonContract.State,
    onCancelClick: () -> Unit,
    onChatClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ContentBackground(
        modifier = modifier,
    ) {
        state.lessonInfo?.let { info ->
            ContentSection(
                titleText = "강습 정보",
            ) {
                SsingMatchingDetailCardSmall(
                    tags = info.tags,
                    teamNicknames = info.teamNicknames,
                    totalCount = info.totalCount,
                    place = info.place,
                    duration = info.duration,
                    price = info.price,
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        InstructorProfileSection(state.instructorProfile)

        Spacer(modifier = Modifier.height(24.dp))

        ParticipantTeamsSection(state.participantTeams)

        Spacer(modifier = Modifier.height(24.dp))

        LessonManagementSection(
            primaryButton = LessonActionButton("강습 취소", onCancelClick),
            secondaryButton = LessonActionButton("채팅방", onChatClick)
        )
    }
}