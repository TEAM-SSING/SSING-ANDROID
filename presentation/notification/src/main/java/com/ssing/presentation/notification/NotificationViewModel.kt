package com.ssing.presentation.notification

import com.ssing.core.ui.base.BaseViewModel
import com.ssing.presentation.notification.model.AlarmType
import com.ssing.presentation.notification.model.AlarmUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import javax.inject.Inject

@HiltViewModel
internal class NotificationViewModel @Inject constructor() :
    BaseViewModel<NotificationContract.State, NotificationContract.Effect>(
        NotificationContract.State()
    ) {

    init {
        loadAlarms()
    }

    // TODO: 실제 알림 API 연동 시 NotificationRepository 로 대체
    private fun loadAlarms() {
        updateState { copy(alarms = MOCK_ALARMS) }
    }
    fun onAlarmClick(alarm: AlarmUiModel) {
        markAsRead(alarm.id)

        if (!alarm.isTargetAvailable) {
            sendEffect(NotificationContract.Effect.ShowToast(INACCESSIBLE_MESSAGE))
            return
        }

        when (alarm.type) {
            AlarmType.LESSON_ARRIVAL ->
                sendEffect(NotificationContract.Effect.NavigateToLessonArrival)

            AlarmType.LESSON_REJECTED ->
                sendEffect(NotificationContract.Effect.NavigateToMatchingLoading)

            AlarmType.LESSON_CONFIRMED ->
                sendEffect(NotificationContract.Effect.NavigateToLessonReady)

        }
    }

    private fun markAsRead(alarmId: Long) {
        updateState {
            copy(
                alarms = alarms
                    .map { if (it.id == alarmId) it.copy(isRead = true) else it }
                    .toImmutableList(),
            )
        }
    }

    companion object {
        private const val INACCESSIBLE_MESSAGE = "이미 만료된 요청이에요"

        private val MOCK_ALARMS = persistentListOf(
            AlarmUiModel(
                id = 2L,
                type = AlarmType.LESSON_ARRIVAL,
                content = "새로운 강습이 도착했어요. 강습생 정보를 확인하고 강습을 수락해보세요.",
                date = "07.04 (토) 12:59",
                isRead = false,
            ),
            AlarmUiModel(
                id = 3L,
                type = AlarmType.LESSON_REJECTED,
                content = "요청 받았던 강습이 거절되었어요. 다른 요청을 받아볼까요?",
                date = "07.04 (토) 12:59",
                isRead = true,
            ),
            AlarmUiModel(
                id = 4L,
                type = AlarmType.LESSON_CONFIRMED,
                content = "요청 받았던 강습이 확정되었어요. 강습생과 채팅하며 강습을 시작해보세요.",
                date = "07.04 (토) 12:59",
                isRead = true,
            ),
        )
    }
}
