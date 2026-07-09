package com.ssing.presentation.instructorlessondetail.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.ssing.core.ui.common.component.TeamNickname
import com.ssing.core.ui.designsystem.theme.Blue50
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.presentation.instructorlessondetail.model.LessonDetailCanceledUiModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun LessonDetailCanceledScreen(
    cancel: LessonDetailCanceledUiModel,
    lessonBannerState: LessonBannerState,
    onBack: () -> Unit,
    onCancelClassClick: () -> Unit,
    onEndClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column {
        SsingTopBar(
            title = "강습 상세",
            onBack = { onBack() },
            backgroundColor = Blue50,
        )
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Blue50),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = SSINGTheme.colors.backgroundNormal,
                        shape = RoundedCornerShape(
                            topStart = 12.dp,
                            topEnd = 12.dp,
                        ),
                    ),
            ) {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                ) {
                    item {
                        LessonBanner(
                            lessonBannerState = lessonBannerState
                        )
                    }
                    item {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Spacer(modifier = Modifier.height(16.dp))

                            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                                SectionTitle(text = "강습 정보")
                                Spacer(modifier = Modifier.height(8.dp))
                                SsingMatchingDetailCardSmall(
                                    tags = cancel.tags,
                                    teamNicknames = cancel.teams.map {
                                        TeamNickname(
                                            it.teamNickname,
                                            it.teamCount
                                        )
                                    }.toPersistentList(),
                                    totalCount = cancel.teams.size,
                                    place = cancel.location,
                                    duration = cancel.duration,
                                    price = cancel.price,
                                )
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                                SectionTitle(text = "강습생 정보")
                                Spacer(modifier = Modifier.height(8.dp))
                                cancel.teams.forEachIndexed { index, team ->
                                    ConsumerInfoCard(
                                        isReady = false,
                                        nickname = team.teamNickname,
                                        participants = team.participants,
                                        price = team.price,
                                    )
                                    if (index != cancel.teams.lastIndex) {
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
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
                                    Spacer(modifier = Modifier.height(16.dp))
                                    SsingButton(
                                        text = "강습 내역 보기",
                                        onClick = onEndClick,
                                        style = SsingButtonStyle.GRAY,
                                        modifier = Modifier.weight(1f),
                                    )
                                }
                            }
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(SSINGTheme.colors.backgroundNormal)
                        .padding(16.dp),
                ) {
                    SsingButton(
                        text = "씽 매칭으로 돌아가기",
                        onClick = { onEndClick() },
                        style = SsingButtonStyle.BLUE,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}



@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = SSINGTheme.typography.caption.sb12,
        color = SSINGTheme.colors.textAlternative,
    )
}

@Preview
@Composable
private fun LessonDetailCanceledScreenPreview() {
    SSINGTheme {
        LessonDetailCanceledScreen(
            cancel = LessonDetailCanceledUiModel(
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
            onEndClick = {},
            lessonBannerState = LessonBannerState.Canceled,
        )
    }
}