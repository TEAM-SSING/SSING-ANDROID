package com.ssing.presentation.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssing.core.ui.common.component.SsingTopBar
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.core.ui.extension.toast
import com.ssing.core.ui.util.HandleUiEffects
import com.ssing.presentation.notification.component.NotificationAlarmCard
import com.ssing.presentation.notification.model.AlarmType
import com.ssing.presentation.notification.model.AlarmUiModel
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun NotificationRoute(
    navigateBack: () -> Unit,
    navigateToLessonArrival: () -> Unit,
    navigateToMatchingLoading: () -> Unit,
    navigateToLessonReady: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NotificationViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    HandleUiEffects(viewModel.uiEffect) { effect ->
        when (effect) {
            NotificationContract.Effect.NavigateToLessonArrival -> navigateToLessonArrival()
            NotificationContract.Effect.NavigateToMatchingLoading -> navigateToMatchingLoading()
            NotificationContract.Effect.NavigateToLessonReady -> navigateToLessonReady()
            is NotificationContract.Effect.ShowToast -> context.toast(effect.message)
        }
    }

    NotificationScreen(
        state = state,
        onBackClick = navigateBack,
        onAlarmClick = viewModel::onAlarmClick,
        modifier = modifier,
    )
}

@Composable
private fun NotificationScreen(
    state: NotificationContract.State,
    onBackClick: () -> Unit,
    onAlarmClick: (AlarmUiModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SSINGTheme.colors.backgroundNormal)
            .statusBarsPadding(),
    ) {
        SsingTopBar(
            title = "알림",
            onBack = onBackClick,
        )

        if (state.isEmpty) {
            NotificationEmptyContent(modifier = Modifier.weight(1f))
        } else {
            LazyColumn {
                items(
                    items = state.alarms,
                    key = { alarm -> alarm.id },
                ) { alarm ->
                    NotificationAlarmCard(
                        category = alarm.type.label,
                        content = alarm.content,
                        date = alarm.date,
                        isRead = alarm.isRead,
                        onClick = { onAlarmClick(alarm) },
                    )
                }
            }

            NotificationFooter(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun NotificationEmptyContent(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(SSINGTheme.colors.backgroundAlternative),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "아직 알림이 없어요",
            style = SSINGTheme.typography.caption.sb14,
            color = SSINGTheme.colors.textAlternative,
        )
    }
}

@Composable
private fun NotificationFooter(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(SSINGTheme.colors.backgroundAlternative)
            .padding(16.dp),
        contentAlignment = Alignment.TopCenter,
    ) {
        Text(
            text = "7일 전 알림까지 확인할 수 있어요",
            style = SSINGTheme.typography.caption.sb12,
            color = SSINGTheme.colors.textAlternative,
            textAlign = TextAlign.Center,
        )
    }
}

private class NotificationStatePreviewProvider :
    PreviewParameterProvider<NotificationContract.State> {
    override val values: Sequence<NotificationContract.State>
        get() = sequenceOf(
            NotificationContract.State(
                alarms = persistentListOf(
                    AlarmUiModel(
                        id = 1L,
                        type = AlarmType.LESSON_ARRIVAL,
                        content = "새로운 강습이 도착했어요. 강습생 정보를 확인하고 강습을 수락해보세요.",
                        date = "07.08 (화) 09:15",
                        isRead = false,
                    ),
                    AlarmUiModel(
                        id = 2L,
                        type = AlarmType.LESSON_CONFIRMED,
                        content = "강습이 확정되었어요. 강습 시작 전 준비를 완료해보세요.",
                        date = "07.07 (월) 18:30",
                        isRead = false,
                    ),
                    AlarmUiModel(
                        id = 3L,
                        type = AlarmType.LESSON_REJECTED,
                        content = "강습생이 강습을 거절했어요. 다른 강습생을 기다려보세요.",
                        date = "07.06 (일) 14:00",
                        isRead = true,
                    ),
                    AlarmUiModel(
                        id = 4L,
                        type = AlarmType.LESSON_ARRIVAL,
                        content = "새로운 강습이 도착했어요. 강습생 정보를 확인하고 강습을 수락해보세요.",
                        date = "07.04 (금) 12:59",
                        isRead = true,
                        isTargetAvailable = false,
                    ),
                ),
            ),
            NotificationContract.State(),
        )
}

@Preview(showBackground = true)
@Composable
private fun NotificationScreenPreview(
    @PreviewParameter(NotificationStatePreviewProvider::class) state: NotificationContract.State,
) {
    SSINGTheme {
        NotificationScreen(
            state = state,
            onBackClick = {},
            onAlarmClick = {},
        )
    }
}
