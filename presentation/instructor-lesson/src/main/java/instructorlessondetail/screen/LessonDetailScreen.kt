package instructorlessondetail.screen
//
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
//import com.ssing.core.ui.common.component.SsingModal
//import com.ssing.core.ui.util.HandleUiEffects
//import instructorlessondetail.LessonDetailContract
//import instructorlessondetail.LessonDetailViewModel
//import kotlinx.collections.immutable.ImmutableList
//
//@Composable
//internal fun LessonDetailRoute(
//    navigateBack: () -> Unit,
//    modifier: Modifier = Modifier,
//    viewModel: LessonDetailViewModel = hiltViewModel(),
//) {
//    val state by viewModel.uiState.collectAsStateWithLifecycle()
//    val context = LocalContext.current
//
//    HandleUiEffects(viewModel.uiEffect) { effect ->
//        when (effect) {
//            is LessonDetailContract.Effect.NavigateBack -> navigateBack()
//        }
//    }
//
//    LessonDetailScreen(
//        state = state,
//        onAction = viewModel::setEvent,
//        modifier = modifier,
//    )
//}
//
//@Composable
//private fun LessonDetailScreen(
//    state: LessonDetailContract.State,
//    isInstructorReady: (Boolean) -> Unit,
//    participantReadyCount: (Int) -> Unit,
//    participantTotalCount: (Int) -> Unit,
//    tags: (ImmutableList<String>) -> Unit,
//    classTitle: (String) -> Unit,
//    location: (String) -> Unit,
//    duration: (String) -> Unit,
//    price: (Int) -> Unit,
//    teams: (ImmutableList<TeamParticipantsInfo>) -> Unit,
//    onBackClick: () -> Unit,
//    onCancelClassClick: () -> Unit,
//    onChatRoomClick: () -> Unit,
//    onReadyClick: () -> Unit,
//    modifier: Modifier = Modifier,
//) {
//    when (val = phase = state.phase) {
//        LessonDetailPhase.SettingCondition -> LessonDetailBeforeScreen(
//            onBackClick = onBackClick,
//            onCancelClassClick = onCancelClassClick,
//            onChatRoomClick = onChatRoomClick,
//            onReadyClick = onReadyClick,
//            modifier = modifier,
//            )
//    }
//    state.dialog?.let { dialog ->
//        LessonDetailDialogHost(
//            dialog = dialog,
//            onStopWaitingConfirm = onStopWaitingConfirm,
//            onContinueMatchingClick = onContinueMatchingClick,
//            onRetryMatchingClick = onRetryMatchingClick,
//            onDialogDismiss = onDialogDismiss,
//        )
//    }
//}
//}
//
//@Composable
//private fun LessonDetailDialogHost(
//    dialog: LessonDetailDialog,
//    onStopWaitingConfirm: () -> Unit,
//    onContinueMatchingClick: () -> Unit,
//    onRetryMatchingClick: () -> Unit,
//    onDialogDismiss: () -> Unit,
//) {
//    when (dialog) {
//        LessonDetailDialog.LessonReady -> SsingModal(
//            onDismissRequest = onDialogDismiss,
//            title = "강습 준비를 완료할까요?",
//            text = "준비 완료 시 변경이 불가능해요",
//            primaryText = "취소",
//            onPrimary = onStopWaitingConfirm,
//            secondaryText = "준비 완료",
//            onSecondary = onDialogDismiss,
//        )
//
//        LessonDetailDialog.LessonDuring -> SsingModal(
//            onDismissRequest = onDialogDismiss,
//            title = "강습을 종료할까요?",
//            text = "강습을 종료하면 모든 참여자의 강습이\n종료 상태로 변경되어요",
//            primaryText = "계속 진행하기",
//            onPrimary = onContinueMatchingClick,
//            secondaryText = "강습 종료하기",
//            onSecondary = onStopWaitingConfirm,
//        )
//    }
//}