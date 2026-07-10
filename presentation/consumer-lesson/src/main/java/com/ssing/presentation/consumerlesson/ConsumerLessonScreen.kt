package com.ssing.presentation.consumerlesson

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssing.core.ui.common.component.CancelReason
import com.ssing.core.ui.common.component.LessonBanner
import com.ssing.core.ui.common.component.LessonBannerState
import com.ssing.core.ui.common.component.MatchingCancelBottomSheet
import com.ssing.core.ui.common.component.SsingTopBar
import com.ssing.core.ui.common.component.UserRole
import com.ssing.core.ui.designsystem.theme.Blue50
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.core.ui.designsystem.theme.White
import com.ssing.core.ui.extension.toast
import com.ssing.core.ui.util.HandleUiEffects
import com.ssing.presentation.consumerlesson.component.BottomButton
import com.ssing.presentation.consumerlesson.component.content.BeforeLessonContent
import com.ssing.presentation.consumerlesson.component.content.CanceledLessonContent
import com.ssing.presentation.consumerlesson.component.content.CompletedLessonContent
import com.ssing.presentation.consumerlesson.component.content.OngoingLessonContent
import com.ssing.presentation.consumerlesson.model.CanceledLessonInfoUiModel
import com.ssing.presentation.consumerlesson.model.CompletedLessonInfoUiModel
import com.ssing.presentation.consumerlesson.model.InstructorProfileUiModel
import com.ssing.presentation.consumerlesson.model.LessonInfoUiModel
import com.ssing.presentation.consumerlesson.model.ParticipantTeamUiModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

@Composable
internal fun ConsumerLessonRoute(
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ConsumerLessonViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    HandleUiEffects(viewModel.uiEffect) { effect ->
        when (effect) {
            is ConsumerLessonContract.Effect.ShowToast -> context.toast(effect.message)
            is ConsumerLessonContract.Effect.NavigationToHome -> navigateToHome()
        }
    }

    ConsumerLessonScreen(
        state = state,
        onReadyClick = viewModel::onReadyClick,
        onCancelClick = viewModel::onCancelClick,
        onReasonSelected = viewModel::onReasonSelected,
        onCancelConfirmed = viewModel::onCancelConfirmed,
        onCancelDismiss = viewModel::onCancelDismiss,
        onChatClick = viewModel::onChatClick,
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
    onChatClick: () -> Unit,
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
                    onChatClick = onChatClick,
                    onCancelClick = onCancelClick,
                )
                is LessonBannerState.Ongoing -> OngoingLessonContent(
                    state,
                    onChatClick = onChatClick,
                )
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
            onChatClick = {}
        )
    }
}