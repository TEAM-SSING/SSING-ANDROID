package com.ssing.presentation.instructorlessondetail.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.common.component.ConsumerInfoCard
import com.ssing.core.ui.common.component.LessonBanner
import com.ssing.core.ui.common.component.LessonBannerState
import com.ssing.core.ui.common.component.SsingButton
import com.ssing.core.ui.common.component.SsingButtonStyle
import com.ssing.core.ui.common.component.SsingMatchingDetailCardSmall
import com.ssing.core.ui.common.component.SsingTopBar
import com.ssing.core.ui.designsystem.theme.Blue50
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.presentation.instructorlessondetail.component.SectionTitle
import com.ssing.presentation.instructorlessondetail.model.LessonDetailOngoingUiModel
import com.ssing.presentation.instructorlessondetail.model.TeamParticipantsInfo
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun LessonDetailOngoingScreen(
    ongoing: LessonDetailOngoingUiModel,
    lessonBannerState: LessonBannerState,
    onBack: () -> Unit,
    onCancelClassClick: () -> Unit,
    onChatRoomClick: () -> Unit,
    onEndClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .navigationBarsPadding()
            .background(Blue50),
    ) {
        SsingTopBar(
            title = "강습 상세",
            onBack = onBack,
            backgroundColor = Blue50,
            modifier = Modifier
                .background(Blue50)
                .statusBarsPadding(),
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            LessonBanner(
                lessonBannerState = lessonBannerState
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .background(color = Blue50)
                    .background(
                        color = SSINGTheme.colors.backgroundNormal,
                        shape = RoundedCornerShape(
                            topStart = 12.dp,
                            topEnd = 12.dp,
                        )
                    )
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                SectionTitle(text = "강습 정보")
                Spacer(modifier = Modifier.height(8.dp))
                SsingMatchingDetailCardSmall(
                    tags = ongoing.tags,
                    teamNicknames = ongoing.nicknames,
                    totalCount = ongoing.teams.size,
                    place = ongoing.location,
                    duration = ongoing.duration,
                    price = ongoing.price,
                )

                Spacer(modifier = Modifier.height(24.dp))
                SectionTitle(text = "강습생 정보")
                Spacer(modifier = Modifier.height(8.dp))
                ongoing.teams.forEachIndexed { index, team ->
                    ConsumerInfoCard(
                        isReady = false,
                        nickname = team.teamNickname,
                        participants = team.participants,
                        price = team.price,
                    )
                    if (index != ongoing.teams.lastIndex) {
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "강습 관리",
                    style = SSINGTheme.typography.caption.sb12,
                    color = SSINGTheme.colors.textAlternative,
                    modifier = Modifier.padding(bottom = 8.dp),
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    SsingButton(
                        text = "문제 신고",
                        onClick = onCancelClassClick,
                        style = SsingButtonStyle.RED,
                        modifier = Modifier.weight(1f),
                    )
                    SsingButton(
                        text = "채팅방",
                        onClick = onChatRoomClick,
                        style = SsingButtonStyle.GRAY,
                        modifier = Modifier.weight(1f),
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        SsingButton(
            text = "강습 종료",
            onClick = onEndClick,
            style = SsingButtonStyle.BLUE,
            modifier = Modifier
                .fillMaxWidth()
                .background(SSINGTheme.colors.backgroundNormal)
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        )
    }
}

@Preview
@Composable
private fun LessonDetailOngoingScreenPreview() {
    SSINGTheme {
        LessonDetailOngoingScreen(
            ongoing = LessonDetailOngoingUiModel(
                tags = persistentListOf("스노보드", "자격증이 있어요"),
                classTitle = "김OO님 팀, 홍지민님 팀 총 5명",
                location = "OOO 리조트",
                duration = "0시간",
                price = 0,
                teams = persistentListOf(
                    TeamParticipantsInfo(
                        teamNickname = "김OO",
                        teamCount = 0,
                        participants = persistentListOf("38세 남", "12세 여", "9세 남"),
                        price = 0,
                        isReady = false,
                    ),
                    TeamParticipantsInfo(
                        teamNickname = "김OO",
                        teamCount = 0,
                        participants = persistentListOf("38세 남", "12세 여", "9세 남"),
                        price = 0,
                        isReady = false,
                    ),
                ),
            ),
            onCancelClassClick = {},
            onBack = {},
            onChatRoomClick = {},
            onEndClick = {},
            lessonBannerState = LessonBannerState.Ongoing(
                remainingTime = "2:59:59",
                elapsedTime = "59분",
            ),
        )
    }
}