package instructorlessondetail.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssing.core.ui.common.component.SsingModal
import com.ssing.core.ui.util.HandleUiEffects
import instructorlessondetail.LessonDetailContract
import instructorlessondetail.LessonDetailContract.LessonDetailPhase
import instructorlessondetail.LessonDetailViewModel

@Composable
internal fun LessonDetailRoute(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LessonDetailViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
//    val context = LocalContext.current

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
        onDialogDismiss = viewModel::onDialogDismiss,
        onChatRoomClick = viewModel::onChatRoomClick,
        onEndClick = viewModel::onEndClick,
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
    onDialogDismiss: () -> Unit,
    onReadyButtonClick: () -> Unit,
    onEndClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (val phase = state.phase) {
        is LessonDetailPhase.Loading -> { }

        is LessonDetailPhase.LessonDetailBefore -> LessonDetailBeforeScreen(
            before = phase.before,
            showReadyDialog = state.showReadyDialog,
            onBackClick = onBackClick,
            onCancelClassClick = onCancelClassClick,
            onChatRoomClick = onChatRoomClick,
            onReadyClick = onReadyClick,
            onDialogDismiss = onDialogDismiss,
            onReadyButtonClick = onReadyButtonClick,
            modifier = modifier,
        )

        is LessonDetailPhase.LessonDetailDuring -> LessonDetailDuringScreen(
            during = phase.during,
            onBackClick = onBackClick,
            onCancelClassClick = onCancelClassClick,
            onChatRoomClick = onChatRoomClick,
            onEndClick = onEndClick,
            modifier = modifier,

            )

        is LessonDetailPhase.LessonDetailAfter -> LessonDetailAfterScreen(
            after = phase.after,
            onBackClick = onBackClick,
            onCancelClassClick = onCancelClassClick,
            onChatRoomClick = onChatRoomClick,
            onEndClick = onEndClick,
            modifier = modifier,
        )

        is LessonDetailPhase.LessonDetailCanceled -> LessonDetailCanceledScreen(
            cancel = phase.cancel,
            onBackClick = onBackClick,
            onCancelClassClick = onCancelClassClick,
            onChatRoomClick = onChatRoomClick,
            onEndClick = onEndClick,
            modifier = modifier,
        )
    }

    state.dialog?.let { dialog ->
        LessonDetailDialogHost(
            dialog = dialog,
            onStopWaitingConfirm = { /* Handle via Event */ },
            onContinueMatchingClick = { /* Handle via Event */ },
            onDialogDismiss = onDialogDismiss,
        )
    }
}


@Composable
private fun LessonDetailDialogHost(
    dialog: LessonDetailContract.LessonDetailDialog,
    onStopWaitingConfirm: () -> Unit,
    onContinueMatchingClick: () -> Unit,
    onDialogDismiss: () -> Unit,
) {
    when (dialog) {
        LessonDetailContract.LessonDetailDialog.InstructorReady -> SsingModal(
            onDismissRequest = onDialogDismiss,
            title = "강습 준비를 완료할까요?",
            text = "준비 완료 시 변경이 불가능해요",
            primaryText = "취소",
            onPrimary = onStopWaitingConfirm,
            secondaryText = "준비 완료",
            onSecondary = onDialogDismiss,
        )

        LessonDetailContract.LessonDetailDialog.LessonEnd -> SsingModal(
            onDismissRequest = onDialogDismiss,
            title = "강습을 종료할까요?",
            text = "강습을 종료하면 모든 참여자의 강습이\n종료 상태로 변경되어요",
            primaryText = "계속 진행하기",
            onPrimary = onContinueMatchingClick,
            secondaryText = "강습 종료하기",
            onSecondary = onStopWaitingConfirm,
        )
    }
}