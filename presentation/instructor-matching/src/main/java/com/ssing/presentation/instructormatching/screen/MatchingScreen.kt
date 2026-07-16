package com.ssing.presentation.instructormatching.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssing.core.ui.common.component.SsingModal
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.core.ui.extension.toast
import com.ssing.core.ui.util.HandleUiEffects
import com.ssing.presentation.instructormatching.MatchingContract
import com.ssing.presentation.instructormatching.MatchingContract.MatchingDialog
import com.ssing.presentation.instructormatching.MatchingContract.MatchingPhase
import com.ssing.presentation.instructormatching.MatchingViewModel
import com.ssing.presentation.instructormatching.model.DurationOption
import com.ssing.presentation.instructormatching.model.LessonSummaryUiModel
import com.ssing.presentation.instructormatching.model.LevelOption
import com.ssing.presentation.instructormatching.model.MatchingExposureUiState
import com.ssing.presentation.instructormatching.model.MatchingOfferUiModel
import com.ssing.presentation.instructormatching.model.MatchingWaitingUiState
import com.ssing.presentation.instructormatching.model.OfferStatusOption
import com.ssing.presentation.instructormatching.model.ParticipantUiModel
import com.ssing.presentation.instructormatching.model.SportOption

@Composable
internal fun MatchingRoute(
    navigateBack: () -> Unit,
    navigateToLessonDetail: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MatchingViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        viewModel.resyncActiveOffer()
    }

    HandleUiEffects(viewModel.uiEffect) { effect ->
        when (effect) {
            is MatchingContract.Effect.ShowToast -> context.toast(effect.message)
            MatchingContract.Effect.NavigateBack -> navigateBack()
            is MatchingContract.Effect.NavigateToLessonDetail -> navigateToLessonDetail(effect.lessonId)
        }
    }

    MatchingScreen(
        state = state,
        onSportClick = viewModel::selectSport,
        onLevelToggle = viewModel::toggleLevel,
        onDurationToggle = viewModel::toggleDuration,
        onMaxHeadcountChange = viewModel::changeMaxHeadcount,
        onNoticeCheckedChange = viewModel::changeNoticeChecked,
        onStartMatchingClick = viewModel::startMatching,
        onEditExposureClick = viewModel::editExposure,
        onStopWaitingClick = viewModel::stopWaiting,
        onAcceptOfferClick = viewModel::acceptOffer,
        onRejectOfferClick = viewModel::rejectOffer,
        onStopWaitingConfirm = viewModel::confirmStopWaiting,
        onDialogDismiss = viewModel::dismissDialog,
        onBackClick = viewModel::onBack,
        modifier = modifier,
    )
}

@Composable
private fun MatchingScreen(
    state: MatchingContract.State,
    onSportClick: (SportOption) -> Unit,
    onLevelToggle: (LevelOption) -> Unit,
    onDurationToggle: (DurationOption) -> Unit,
    onMaxHeadcountChange: (Int) -> Unit,
    onNoticeCheckedChange: (Boolean) -> Unit,
    onStartMatchingClick: () -> Unit,
    onEditExposureClick: () -> Unit,
    onStopWaitingClick: () -> Unit,
    onAcceptOfferClick: () -> Unit,
    onRejectOfferClick: () -> Unit,
    onStopWaitingConfirm: () -> Unit,
    onDialogDismiss: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (val phase = state.phase) {
        MatchingPhase.SettingExposure -> MatchingExposureScreen(
            exposure = state.exposure,
            onSportClick = onSportClick,
            onLevelToggle = onLevelToggle,
            onDurationToggle = onDurationToggle,
            onMaxHeadcountChange = onMaxHeadcountChange,
            onNoticeCheckedChange = onNoticeCheckedChange,
            onStartMatchingClick = onStartMatchingClick,
            onBackClick = onBackClick,
            modifier = modifier,
        )

        MatchingPhase.Waiting -> MatchingWaitingScreen(
            exposure = state.exposure,
            waiting = state.waiting,
            onBackClick = onBackClick,
            onEditExposureClick = onEditExposureClick,
            onStopWaitingClick = onStopWaitingClick,
            modifier = modifier,
        )

        is MatchingPhase.OfferArrived -> MatchingOfferScreen(
            offer = phase.offer,
            onBackClick = onBackClick,
            onRejectOfferClick = onRejectOfferClick,
            onAcceptOfferClick = onAcceptOfferClick,
            modifier = modifier,
        )

        is MatchingPhase.PendingConfirm -> MatchingPendingScreen(
            offer = phase.offer,
            onBackClick = onBackClick,
            modifier = modifier,
        )
    }

    state.dialog?.let { dialog ->
        MatchingDialogHost(
            dialog = dialog,
            onStopWaitingConfirm = onStopWaitingConfirm,
            onDialogDismiss = onDialogDismiss,
        )
    }
}

@Composable
private fun MatchingDialogHost(
    dialog: MatchingDialog,
    onStopWaitingConfirm: () -> Unit,
    onDialogDismiss: () -> Unit,
) {
    when (dialog) {
        MatchingDialog.StopWaiting -> SsingModal(
            onDismissRequest = onDialogDismiss,
            title = "대기를 중지할까요?",
            text = "홈으로 이동해도 씽 매칭 대기는 유지돼요.\n대기를 중지하면 더 이상 요청을 받지 않아요.",
            primaryText = "대기 중지",
            onPrimary = onStopWaitingConfirm,
            secondaryText = "계속 대기",
            onSecondary = onDialogDismiss,
        )
    }
}


private val previewExposure = MatchingExposureUiState(
    availableSports = SportOption.entries.toSet(),
    resortName = "하이원 리조트",
    selectedSports = SportOption.SKI,
    selectedLevels = setOf(LevelOption.BEGINNER),
    selectedDurations = setOf(DurationOption.HOUR_3),
    maxHeadcount = 4,
    isNoticeChecked = true,
)

private val previewOffer = MatchingOfferUiModel(
    offerId = 1L,
    groupId = 1L,
    status = OfferStatusOption.OFFERED,
    expiresAtMillis = null,
    nickname = "홍지민",
    teamCount = 4,
    classDateTime = "강습생과 만난 직후 강습 시작",
    participants = listOf(
        ParticipantUiModel(age = 28, isMale = true),
        ParticipantUiModel(age = 25, isMale = false),
    ),
    isPaid = false,
    price = 87500,
    equipmentStatus = "착용 완료",
    lesson = LessonSummaryUiModel(
        resortLabel = "하이원 리조트",
        sportLabel = "스키",
        levelLabel = "처음타요",
        headcount = 4,
        durationHours = 3,
    ),
)

private val previewWaiting = MatchingWaitingUiState(
    nickname = "홍지민",
    teamCount = 2,
    classDateTime = "강습생과 만난 직후 강습 시작",
    participants = listOf(
        ParticipantUiModel(age = 28, isMale = true),
        ParticipantUiModel(age = 25, isMale = false),
    ),
    isPaid = false,
    price = 87500,
    equipmentStatus = "착용 완료",
)

private fun <T> Set<T>.toggle(item: T): Set<T> = if (item in this) this - item else this + item


@Preview
@Composable
private fun MatchingFlowExposurePreview() {
    var state by remember { mutableStateOf(MatchingContract.State(exposure = previewExposure)) }
    SSINGTheme {
        MatchingScreen(
            state = state,
            onSportClick = { state = state.copy(exposure = state.exposure.copy(selectedSports = it)) },
            onLevelToggle = { state = state.copy(exposure = state.exposure.copy(selectedLevels = state.exposure.selectedLevels.toggle(it))) },
            onDurationToggle = { state = state.copy(exposure = state.exposure.copy(selectedDurations = state.exposure.selectedDurations.toggle(it))) },
            onMaxHeadcountChange = { state = state.copy(exposure = state.exposure.copy(maxHeadcount = it)) },
            onNoticeCheckedChange = { state = state.copy(exposure = state.exposure.copy(isNoticeChecked = it)) },
            onStartMatchingClick = { state = state.copy(phase = MatchingPhase.Waiting, waiting = previewWaiting) },
            onEditExposureClick = { state = state.copy(phase = MatchingPhase.SettingExposure) },
            onStopWaitingClick = { state = state.copy(dialog = MatchingDialog.StopWaiting) },
            onAcceptOfferClick = { (state.phase as? MatchingPhase.OfferArrived)?.let { state = state.copy(phase = MatchingPhase.PendingConfirm(it.offer)) } },
            onRejectOfferClick = { state = state.copy(phase = MatchingPhase.Waiting) },
            onStopWaitingConfirm = { state = state.copy(dialog = null, phase = MatchingPhase.SettingExposure) },
            onDialogDismiss = { state = state.copy(dialog = null) },
            onBackClick = {},
        )
    }
}

@Preview
@Composable
private fun MatchingFlowOfferPreview() {
    var state by remember {
        mutableStateOf(
            MatchingContract.State(
                exposure = previewExposure,
                phase = MatchingPhase.OfferArrived(previewOffer),
            )
        )
    }
    SSINGTheme {
        MatchingScreen(
            state = state,
            onSportClick = {},
            onLevelToggle = {},
            onDurationToggle = {},
            onMaxHeadcountChange = {},
            onNoticeCheckedChange = {},
            onStartMatchingClick = {},
            onEditExposureClick = { state = state.copy(phase = MatchingPhase.SettingExposure) },
            onStopWaitingClick = { state = state.copy(dialog = MatchingDialog.StopWaiting) },
            onAcceptOfferClick = { (state.phase as? MatchingPhase.OfferArrived)?.let { state = state.copy(phase = MatchingPhase.PendingConfirm(it.offer)) } },
            onRejectOfferClick = { state = state.copy(phase = MatchingPhase.Waiting, waiting = previewWaiting) },
            onStopWaitingConfirm = { state = state.copy(dialog = null, phase = MatchingPhase.SettingExposure) },
            onDialogDismiss = { state = state.copy(dialog = null) },
            onBackClick = {},
        )
    }
}
