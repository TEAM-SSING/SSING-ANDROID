package com.ssing.presentation.consumerlesson

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssing.core.ui.common.component.CancelReason
import com.ssing.core.ui.common.component.ConsumerInfoCard
import com.ssing.core.ui.common.component.InstructorProfileButton
import com.ssing.core.ui.common.component.LessonBanner
import com.ssing.core.ui.common.component.LessonBannerState
import com.ssing.core.ui.common.component.MatchingCancelBottomSheet
import com.ssing.core.ui.common.component.SsingButton
import com.ssing.core.ui.common.component.SsingButtonStyle
import com.ssing.core.ui.common.component.SsingMatchingDetailCardSmall
import com.ssing.core.ui.common.component.SsingTopBar
import com.ssing.core.ui.common.component.UserRole
import com.ssing.core.ui.designsystem.theme.Blue50
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.core.ui.designsystem.theme.White
import com.ssing.presentation.consumerlesson.model.CanceledLessonInfoUiModel
import com.ssing.presentation.consumerlesson.model.CompletedLessonInfoUiModel
import com.ssing.presentation.consumerlesson.model.InstructorProfileUiModel
import com.ssing.presentation.consumerlesson.model.LessonInfoUiModel
import com.ssing.presentation.consumerlesson.model.ParticipantTeamUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

@Composable
internal fun ConsumerLessonRoute(
    modifier: Modifier = Modifier,
    viewModel: ConsumerLessonViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ConsumerLessonScreen(
        state = state,
        onReadyClick = viewModel::onReadyClick,
        onCancelClick = viewModel::onCancelClick,
        onReasonSelected = viewModel::onReasonSelected,
        onCancelConfirmed = viewModel::onCancelConfirmed,
        onCancelDismiss = viewModel::onCancelDismiss,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ConsumerLessonScreen(
    state: ConsumerLessonContract.State,
    onReadyClick: () -> Unit,
    onCancelClick: () -> Unit,
    onReasonSelected: (CancelReason) -> Unit,
    onCancelConfirmed: (String?) -> Unit,
    onCancelDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val etcState = rememberTextFieldState()

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

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            LessonBanner(
                lessonBannerState = state.lessonBannerState,
                beforeLessonText = "강사님과 만난 후\n강습시작을 눌러주세요",
            )

            when (state.lessonBannerState) {
                is LessonBannerState.Before -> BeforeLessonContent(
                    state,
                    onCancelClick = onCancelClick,
                )
                is LessonBannerState.Ongoing -> OngoingLessonContent(state)
                is LessonBannerState.Completed -> CompletedLessonContent(state)
                is LessonBannerState.Canceled -> CanceledLessonContent(state)
            }
        }

        BottomButton(
            state = state,
            onClick = {
                // TODO: LessonState 단계별 내비게이션
            },
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp,
                )
        )
    }

    if (state.showCancelConfirmSheet) {
        MatchingCancelBottomSheet(
            userRole = UserRole.CONSUMER,
            selectedReason = state.selectedReason,
            onReasonClick = onReasonSelected,
            etcState = etcState,
            onConfirmClick = onCancelConfirmed,
            onDismissRequest = onCancelDismiss,
        )
    }
}

@Composable
private fun BeforeLessonContent(
    state: ConsumerLessonContract.State,
    onCancelClick: () -> Unit,
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
            secondaryButton = LessonActionButton("채팅방", {})
        )
    }
}

@Composable
private fun OngoingLessonContent(
    state: ConsumerLessonContract.State,
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
            primaryButton = LessonActionButton("문제 신고", {}),
            secondaryButton = LessonActionButton("채팅방", {})
        )
    }
}

@Composable
private fun CompletedLessonContent(
    state: ConsumerLessonContract.State,
    modifier: Modifier = Modifier,
) {
    ContentBackground(
        modifier = modifier,
    ) {
        state.completedLessonInfo?.let { info ->
            ContentSection(
                titleText = "강습 정보",
            ) {
                SsingMatchingDetailCardSmall(
                    tags = info.lessonInfo.tags,
                    teamNicknames = info.lessonInfo.teamNicknames,
                    totalCount = info.lessonInfo.totalCount,
                    place = info.lessonInfo.place,
                    duration = info.lessonInfo.duration,
                    actualTimeRange = info.actualTimeRange,
                    price = info.lessonInfo.price,
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        InstructorProfileSection(state.instructorProfile)

        Spacer(modifier = Modifier.height(24.dp))

        LessonManagementSection(
            primaryButton = LessonActionButton("문제 신고", {}),
            secondaryButton = LessonActionButton("이 강사님 추가 예약", {})
        )
    }
}

@Composable
private fun CanceledLessonContent(
    state: ConsumerLessonContract.State,
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
            primaryButton = LessonActionButton("문제 신고", {}),
            secondaryButton = LessonActionButton("강습 내역 보기", {})
        )
    }
}

@Composable
private fun InstructorProfileSection(
    instructorProfile: InstructorProfileUiModel?,
    modifier: Modifier = Modifier,
) {
    instructorProfile?.let { info ->
        ContentSection(
            titleText = "강사 프로필",
            spacer = 4,
            modifier = modifier,
        ) {
            InstructorProfileButton(
                name = info.name,
                age = info.age,
                gender = info.gender,
                level = info.level,
                imageUrl = info.imageUrl,
                onClick = {},
            )
        }
    }
}

@Composable
private fun ParticipantTeamsSection(
    participantTeams: ImmutableList<ParticipantTeamUiModel>,
    modifier: Modifier = Modifier,
) {
    participantTeams.let { info ->
        ContentSection(
            titleText = "강습생 정보",
            modifier = modifier,
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                info.forEach { team ->
                    ConsumerInfoCard(
                        isReady = team.isReady,
                        nickname = team.nickname,
                        participants = team.participants,
                    )
                }
            }
        }
    }
}

private data class LessonActionButton(
    val text: String,
    val onClick: () -> Unit,
)

@Composable
private fun LessonManagementSection(
    primaryButton: LessonActionButton,
    secondaryButton: LessonActionButton,
    modifier: Modifier = Modifier,
) {
    ContentSection(
        titleText = "강습 관리",
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            SsingButton(
                text = primaryButton.text,
                onClick = primaryButton.onClick,
                style = SsingButtonStyle.RED,
                modifier = Modifier.weight(1f),
            )
            SsingButton(
                text = secondaryButton.text,
                onClick = secondaryButton.onClick,
                style = SsingButtonStyle.GRAY,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun ContentBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Blue50)
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

private class LessonBannerStatePreviewProvider : PreviewParameterProvider<LessonBannerState> {
    override val values = sequenceOf(
        LessonBannerState.Before(
            isInstructorReady = false,
            participantReadyCount = 2,
            participantTotalCount = 5,
        ),
        LessonBannerState.Ongoing(
            remainingTime = "2:59:59",
            elapsedTime = "59분",
        ),
        LessonBannerState.Completed(
            lessonDate = "2026년 12월 31일",
        ),
        LessonBannerState.Canceled,
    )
}

@Preview(showBackground = true, heightDp = 800)
@Composable
private fun ConsumerLessonScreenPreview(
    @PreviewParameter(LessonBannerStatePreviewProvider::class) bannerState: LessonBannerState,
) {
    SSINGTheme {
        val sampleLessonInfo = LessonInfoUiModel(
            tags = persistentListOf("스노보드", "자격증이 있어요"),
            teamNicknames = persistentListOf("김멍멍", "김야옹"),
            totalCount = 2,
            place = "000 리조트",
            duration = "2시간",
            price = 500000,
        )

        val baseParticipantTeams = persistentListOf(
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

        val participantTeams = if (bannerState is LessonBannerState.Before) {
            baseParticipantTeams
        } else {
            baseParticipantTeams.map { it.copy(isReady = false) }.toPersistentList()
        }

        val state = ConsumerLessonContract.State(
            lessonBannerState = bannerState,
            isReady = false,
            lessonInfo = sampleLessonInfo,
            instructorProfile = InstructorProfileUiModel(
                name = "김어흥 강사",
                age = 27,
                gender = "남",
                level = "grade1",
                imageUrl = "",
            ),
            participantTeams = participantTeams,
            completedLessonInfo = CompletedLessonInfoUiModel(
                lessonInfo = sampleLessonInfo,
                actualTimeRange = "14:00 - 16:00 (2시간)",
            ),
            canceledLessonInfo = CanceledLessonInfoUiModel(
                lessonInfo = sampleLessonInfo,
                cancelDateTime = "2026.07.10 14:00",
                cancelSubject = "강습생",
                cancelReason = "일정 변경",
            ),
        )

        ConsumerLessonScreen(
            state = state,
            onReadyClick = {},
            onCancelClick = {},
            onReasonSelected = {},
            onCancelConfirmed = {},
            onCancelDismiss = {},
        )
    }
}