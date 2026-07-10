package com.ssing.presentation.instructorlessondetail.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssing.core.ui.common.component.CancelReason
import com.ssing.core.ui.common.component.LessonBannerState
import com.ssing.core.ui.common.component.SsingModal
import com.ssing.core.ui.util.HandleUiEffects
import com.ssing.presentation.instructorlessondetail.LessonDetailContract
import com.ssing.presentation.instructorlessondetail.LessonDetailContract.LessonDetailPhase
import com.ssing.presentation.instructorlessondetail.LessonDetailViewModel

@Composable
internal fun LessonDetailRoute(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LessonDetailViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    HandleUiEffects(viewModel.uiEffect) { effect ->
        when (effect) {
            is LessonDetailContract.Effect.NavigateBack -> navigateBack()
        }
    }

    LessonDetailScreen(
        state = state,
        onBackClick = viewModel::onBackClick,
        onCancelClassClick = viewModel::onCancelClassClick,
        onReadyButtonClick = viewModel::onReadyButtonClick,
        onReadyClick = viewModel::onReadyClick,
        onReadyDialogDismiss = viewModel::onReadyDialogDismiss,
        onChatRoomClick = viewModel::onChatRoomClick,
        onEndClick = viewModel::onEndClick,
        onLessonEndDialogDismiss = viewModel::onLessonEndDialogDismiss,
        onContinueClick = viewModel::onContinueClick,
        onCancelReasonSelect = viewModel::onCancelReasonSelect,
        onEtcReasonTextChange = viewModel::onEtcReasonTextChange,
        modifier = modifier,
    )
}


@Composable
private fun LessonDetailScreen(
    state: LessonDetailContract.State,
    onBackClick: () -> Unit,
    onCancelClassClick: () -> Unit,
    onChatRoomClick: () -> Unit,
    onReadyClick: () -> Unit,
    onReadyDialogDismiss: () -> Unit,
    onReadyButtonClick: () -> Unit,
    onEndClick: () -> Unit,
    onLessonEndDialogDismiss: () -> Unit,
    onContinueClick: () -> Unit,
    onCancelReasonSelect: (CancelReason) -> Unit,
    onEtcReasonTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (val phase = state.phase) {
        is LessonDetailPhase.Loading -> { }

        is LessonDetailPhase.LessonDetailBefore -> LessonDetailBeforeScreen(
            before = phase.before,
            lessonBannerState = LessonBannerState.Before(
                isInstructorReady = phase.before.isInstructorReady,
                participantReadyCount = phase.before.participantReadyCount,
                participantTotalCount = phase.before.participantTotalCount,
            ),
            cancelReasonState = state.cancelReasonState,
            onCancelReasonSelect = onCancelReasonSelect,
            onEtcReasonTextChange = onEtcReasonTextChange,
            onCancelClassClick = onCancelClassClick,
            onBack = onBackClick,
            onChatRoomClick = onChatRoomClick,
            onReadyClick = onReadyClick,
            onReadyButtonClick = onReadyButtonClick,
            modifier = modifier,
        )

        is LessonDetailPhase.LessonDetailOngoing -> LessonDetailOngoingScreen(
            ongoing = phase.ongoing,
            lessonBannerState = LessonBannerState.Ongoing(
                remainingTime = phase.ongoing.remainingTime,
                elapsedTime = phase.ongoing.elapsedTime,
            ),
            onCancelClassClick = onCancelClassClick,
            onBack = onBackClick,
            onChatRoomClick = onChatRoomClick,
            onEndClick = onEndClick,
            modifier = modifier,
            )

        is LessonDetailPhase.LessonDetailCompleted -> LessonDetailCompletedScreen(
            completed = phase.completed,
            lessonBannerState = LessonBannerState.Completed(
                lessonDate = phase.completed.lessonDate,
            ),
            onBack = onBackClick,
            onChatRoomClick = onChatRoomClick,
            onEndClick = onEndClick,
            modifier = modifier,
        )

        is LessonDetailPhase.LessonDetailCanceled -> LessonDetailCanceledScreen(
            cancel = phase.cancel,
            lessonBannerState = LessonBannerState.Canceled,
            onBack = onBackClick,
            onCancelClassClick = onCancelClassClick,
            onEndClick = onEndClick,
            modifier = modifier,
        )
    }

    if (state.showReadyDialog) {
        SsingModal(
            onDismissRequest = onReadyDialogDismiss,
            title = "강습 준비를 완료할까요?",
            text = "준비 완료 시 변경이 불가능해요",
            primaryText = "준비 완료",
            onPrimary = onReadyClick,
            secondaryText = "취소",
            onSecondary = onReadyDialogDismiss,
        )
    }

    if (state.showLessonEndDialog) {
        SsingModal(
            onDismissRequest = onLessonEndDialogDismiss,
            title = "강습을 종료할까요?",
            text = "강습을 종료하면 모든 참여자의 강습이\n종료 상태로 변경되어요",
            primaryText = "강습 종료하기",
            onPrimary = onEndClick,
            secondaryText = "계속 진행하기",
            onSecondary = onContinueClick,
        )
    }
}


@Composable
private fun LessonDetailDialogHost(
    dialog: LessonDetailContract.LessonDetailDialog,
    showReadyDialog: LessonDetailContract.State,
    onReadyButtonClick: () -> Unit,
    onStopWaitingConfirm: () -> Unit,
    onContinueMatchingClick: () -> Unit,
    onEndClick: () -> Unit,
    onDialogDismiss: () -> Unit,
) {
    when (dialog) {
        LessonDetailContract.LessonDetailDialog.InstructorReady -> SsingModal(
            onDismissRequest = onDialogDismiss,
            title = "강습 준비를 완료할까요?",
            text = "준비 완료 시 변경이 불가능해요",
            primaryText = "준비 완료",
            onPrimary = onReadyButtonClick,
            secondaryText = "취소",
            onSecondary = onEndClick,
        )

        LessonDetailContract.LessonDetailDialog.LessonEnd -> SsingModal(
            onDismissRequest = onDialogDismiss,
            title = "강습을 종료할까요?",
            text = "강습을 종료하면 모든 참여자의 강습이\n종료 상태로 변경되어요",
            primaryText = "강습 종료하기",
            onPrimary = onStopWaitingConfirm,
            secondaryText = "계속 진행하기",
            onSecondary = onDialogDismiss,
        )
    }
}