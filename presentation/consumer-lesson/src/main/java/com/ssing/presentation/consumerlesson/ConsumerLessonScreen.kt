package com.ssing.presentation.consumerlesson

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssing.core.ui.common.component.ConsumerInfoCard
import com.ssing.core.ui.common.component.InstructorProfileButton
import com.ssing.core.ui.common.component.LessonBanner
import com.ssing.core.ui.common.component.LessonBannerState
import com.ssing.core.ui.common.component.SsingButton
import com.ssing.core.ui.common.component.SsingButtonStyle
import com.ssing.core.ui.common.component.SsingMatchingDetailCardSmall
import com.ssing.core.ui.common.component.SsingTopBar
import com.ssing.core.ui.designsystem.theme.Blue50
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.core.ui.designsystem.theme.White
import com.ssing.presentation.consumerlesson.model.InstructorProfileUiModel
import com.ssing.presentation.consumerlesson.model.LessonInfoUiModel
import com.ssing.presentation.consumerlesson.model.ParticipantTeamUiModel
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun ConsumerLessonRoute(
    modifier: Modifier = Modifier,
    viewModel: ConsumerLessonViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ConsumerLessonScreen(
        state = state,
        modifier = modifier,
    )
}

@Composable
private fun ConsumerLessonScreen(
    state: ConsumerLessonContract.State,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(White)
    ) {
        SsingTopBar(
            title = "강습 상세",
            onBack = {
                // TODO: 홈으로 이동
            },
            backgroundColor = Blue50,
        )

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            item {
                LessonBanner(
                    lessonBannerState = state.lessonBannerState,
                    beforeLessonText = "강사님과 만난 후\n강습시작을 눌러주세요",
                )
            }

            item {
                when (state.lessonBannerState) {
                    is LessonBannerState.Before -> BeforeLessonContent(state)
                    is LessonBannerState.Ongoing -> OngoingLessonContent(state)
                    is LessonBannerState.Completed -> CompletedLessonContent(state)
                    is LessonBannerState.Canceled -> CanceledLessonContent(state)
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp,
                ),
            contentAlignment = Alignment.BottomCenter,
        ) {
            BottomButton(
                state = state,
                onClick = {
                    // TODO: LessonState 단계별 내비게이션
                },
            )
        }

    }
}

@Composable
private fun BeforeLessonContent(
    state: ConsumerLessonContract.State,
    modifier: Modifier = Modifier,
) {
    ContentBackground {
        ContentSection(
            titleText = "강습 정보",
        ) {
            val lessonInfo = state.lessonInfo ?: return@ContentSection

            SsingMatchingDetailCardSmall(
                tags = lessonInfo.tags,
                teamNicknames = lessonInfo.teamNicknames,
                totalCount = lessonInfo.totalCount,
                place = lessonInfo.place,
                duration = lessonInfo.duration,
                price = lessonInfo.price,
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        ContentSection(
            titleText = "강사 프로필",
        ) {
            val instructorProfile = state.instructorProfile ?: return@ContentSection

            InstructorProfileButton(
                name = instructorProfile.name,
                age = instructorProfile.age,
                gender = instructorProfile.gender,
                level = instructorProfile.level,
                imageUrl = instructorProfile.imageUrl,
                onClick = {},
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        ContentSection(
            titleText = "강습생 정보",
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                state.participantTeams.forEach { team ->
                    ConsumerInfoCard(
                        isReady = team.isReady,
                        nickname = team.nickname,
                        participants = team.participants,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        ContentSection(
            titleText = "강습 관리",
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                SsingButton(
                    text = "강습 취소",
                    onClick = {
                        // TODO: 강습 취소 바텀시트 노출
                    },
                    style = SsingButtonStyle.RED,
                    modifier = Modifier.weight(1f),
                )

                SsingButton(
                    text = "채팅방",
                    onClick = {},
                    style = SsingButtonStyle.GRAY,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
private fun OngoingLessonContent(
    state: ConsumerLessonContract.State,
    modifier: Modifier = Modifier,
) {
    // TODO: 강습 진행 중 UI 구현
}

@Composable
private fun CompletedLessonContent(
    state: ConsumerLessonContract.State,
    modifier: Modifier = Modifier,
) {
    // TODO: 강습 진행 후 UI 구현
}

@Composable
private fun CanceledLessonContent(
    state: ConsumerLessonContract.State,
    modifier: Modifier = Modifier,
) {
    // TODO: 강습 취소 UI 구현
}

@Composable
private fun ContentBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
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
                    )
                )
                .padding(16.dp),
        ) {
            content()
        }
    }
}

@Composable
private fun ContentSection(
    titleText: String,
    modifier: Modifier = Modifier,
    spacer: Int = 8,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(spacer.dp),
    ) {
        Text(
            text = titleText,
            color = SSINGTheme.colors.textAlternative,
            style = SSINGTheme.typography.caption.sb12,
        )

        content()
    }
}

@Composable
private fun BottomButton(
    state: ConsumerLessonContract.State,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val buttonText = when (state.lessonBannerState) {
        is LessonBannerState.Before -> if (state.isReady) {
            "강습 대기중"
        } else {
            "강습 준비 완료"
        }

        is LessonBannerState.Ongoing -> "강습 종료"
        is LessonBannerState.Completed -> "리뷰 쓰기"
        is LessonBannerState.Canceled -> "홈으로 돌아가기"
    }

    SsingButton(
        text = buttonText,
        onClick = onClick,
        style = SsingButtonStyle.BLUE,
        modifier = modifier.fillMaxWidth(),
    )
}

@Preview(showBackground = true)
@Composable
private fun ConsumerLessonScreenPreview() {
    SSINGTheme {
        ConsumerLessonScreen(
            state = ConsumerLessonContract.State(
                lessonBannerState = LessonBannerState.Before(
                    isInstructorReady = false,
                    participantReadyCount = 2,
                    participantTotalCount = 5,
                ),
                lessonInfo = LessonInfoUiModel(
                    tags = persistentListOf("스노보드", "자격증이 있어요"),
                    teamNicknames = persistentListOf(
                        "김멍멍", "김야옹"
                    ),
                    totalCount = 2,
                    place = "000 리조트",
                    duration = "0시간",
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
                )
            ),
        )
    }
}