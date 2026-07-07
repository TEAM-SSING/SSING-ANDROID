package com.ssing.presentation.instructormatching.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssing.core.ui.common.component.SsingButtonStyle
import com.ssing.core.ui.common.component.SsingModal
import com.ssing.core.ui.extension.toast
import com.ssing.core.ui.util.HandleUiEffects
import com.ssing.presentation.instructormatching.MatchingContract
import com.ssing.presentation.instructormatching.MatchingContract.MatchingDialog
import com.ssing.presentation.instructormatching.MatchingContract.MatchingPhase
import com.ssing.presentation.instructormatching.MatchingViewModel

@Composable
internal fun MatchingRoute(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MatchingViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    HandleUiEffects(viewModel.uiEffect) { effect ->
        when (effect) {
            is MatchingContract.Effect.ShowToast -> context.toast(effect.message)
            MatchingContract.Effect.NavigateBack -> navigateBack()
        }
    }

    MatchingScreen(
        state = state,
        onIntent = viewModel::processIntent,
        modifier = modifier,
    )
}

@Composable
private fun MatchingScreen(
    state: MatchingContract.State,
    onIntent: (MatchingContract.Intent) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (val phase = state.phase) {
        MatchingPhase.SettingCondition -> MatchingConditionScreen(
            condition = state.condition,
            onIntent = onIntent,
            modifier = modifier,
        )

        MatchingPhase.Waiting -> MatchingWaitingScreen(
            onIntent = onIntent,
            modifier = modifier,
        )

        is MatchingPhase.OfferArrived -> MatchingOfferScreen(
            offer = phase.offer,
            onIntent = onIntent,
            modifier = modifier,
        )

        is MatchingPhase.PendingConfirm -> MatchingPendingScreen(
            offer = phase.offer,
            onIntent = onIntent,
            modifier = modifier,
        )
    }

    state.dialog?.let { dialog ->
        MatchingDialogHost(
            dialog = dialog,
            onIntent = onIntent,
        )
    }
}

@Composable
private fun MatchingDialogHost(
    dialog: MatchingDialog,
    onIntent: (MatchingContract.Intent) -> Unit,
) {
    when (dialog) {
        MatchingDialog.StopWaiting -> SsingModal(
            onDismissRequest = { onIntent(MatchingContract.Intent.OnDialogDismiss) },
            title = "대기를 중지할까요?",
            text = "홈으로 이동해도 씽 매칭 대기는 유지돼요.\n대기를 중지하면 더 이상 요청을 받지 않아요.",
            primaryText = "대기 중지",
            onPrimary = { onIntent(MatchingContract.Intent.OnStopWaitingConfirm) },
            secondaryText = "계속 대기",
            onSecondary = { onIntent(MatchingContract.Intent.OnDialogDismiss) },
        )

        MatchingDialog.ConsumerRejected -> SsingModal(
            onDismissRequest = { onIntent(MatchingContract.Intent.OnDialogDismiss) },
            title = "강습생이 매칭을 거절했어요",
            text = "이전에 설정한 조건을 유지한 채\n씽 매칭을 계속 할까요?",
            primaryText = "계속하기",
            onPrimary = { onIntent(MatchingContract.Intent.OnContinueMatchingClick) },
            secondaryText = "그만두기",
            onSecondary = { onIntent(MatchingContract.Intent.OnStopWaitingConfirm) },
        )

        MatchingDialog.MatchingFailed -> SsingModal(
            onDismissRequest = { onIntent(MatchingContract.Intent.OnDialogDismiss) },
            title = "매칭이 실패했어요",
            text = "연결 상태를 확인한 후 다시 시도해주세요",
            primaryText = "계속하기",
            onPrimary = { onIntent(MatchingContract.Intent.OnRetryMatchingClick) },
            secondaryText = "그만하기",
            onSecondary = { onIntent(MatchingContract.Intent.OnDialogDismiss) },
            secondaryStyle = SsingButtonStyle.GRAY,
        )
    }
}
