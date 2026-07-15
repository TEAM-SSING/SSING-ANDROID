package com.ssing.presentation.notification

import androidx.lifecycle.viewModelScope
import com.ssing.core.network.exception.ApiException
import com.ssing.core.ui.base.BaseViewModel
import com.ssing.core.ui.extension.uiMessage
import com.ssing.data.notification.model.NotificationItem
import com.ssing.data.notification.model.NotificationType
import com.ssing.data.notification.repository.api.NotificationRepository
import com.ssing.presentation.notification.model.AlarmType
import com.ssing.presentation.notification.model.AlarmUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
internal class NotificationViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository,
) : BaseViewModel<NotificationContract.State, NotificationContract.Effect>(
    NotificationContract.State()
) {

    private var nextCursor: String? = null
    private var isLastPage = false

    init {
        loadFirstPage()
    }

    private fun loadFirstPage() {
        updateState { copy(isLoading = true) }
        viewModelScope.launch {
            notificationRepository.getNotifications(cursor = null, size = PAGE_SIZE)
                .onSuccess { page ->
                    nextCursor = page.nextCursor
                    isLastPage = !page.hasNext
                    updateState {
                        copy(
                            isLoading = false,
                            alarms = page.notifications.map { it.toUiModel() }.toImmutableList(),
                        )
                    }
                }
                .onFailure { handleLoadError(it, isFirstPage = true) }
        }
    }

    /** 리스트 끝에 도달하면 호출. 다음 페이지를 이어붙인다. */
    fun loadMore() {
        if (isLastPage) return
        val state = uiState.value
        if (state.isLoading || state.isLoadingMore) return

        updateState { copy(isLoadingMore = true) }
        viewModelScope.launch {
            notificationRepository.getNotifications(cursor = nextCursor, size = PAGE_SIZE)
                .onSuccess { page ->
                    nextCursor = page.nextCursor
                    isLastPage = !page.hasNext
                    updateState {
                        copy(
                            isLoadingMore = false,
                            alarms = (alarms + page.notifications.map { it.toUiModel() })
                                .toImmutableList(),
                        )
                    }
                }
                .onFailure { handleLoadError(it, isFirstPage = false) }
        }
    }

    private fun handleLoadError(throwable: Throwable, isFirstPage: Boolean) {
        Timber.e(throwable, "알림 목록 조회 실패 (firstPage=$isFirstPage)")
        updateState { copy(isLoading = false, isLoadingMore = false) }
        if (throwable is ApiException) {
            sendEffect(NotificationContract.Effect.ShowToast(throwable.uiMessage))
        }
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

    private fun NotificationItem.toUiModel() = AlarmUiModel(
        id = id,
        type = type.toAlarmType(),
        title = title,
        content = body,
        date = createdAt.toDisplayDate(),
        isRead = isRead,
    )

    private fun NotificationType.toAlarmType() = when (this) {
        NotificationType.MATCHING_OFFER_RECEIVED -> AlarmType.LESSON_ARRIVAL
        NotificationType.MATCHING_OFFER_CLOSED -> AlarmType.LESSON_REJECTED
        NotificationType.MATCHING_CONFIRMED -> AlarmType.LESSON_CONFIRMED
        NotificationType.UNKNOWN -> AlarmType.LESSON_ARRIVAL
    }

    /** ISO-8601(UTC) → 기기 시간대의 "MM.dd (E) HH:mm" 표시 문자열. 파싱 실패 시 원문 유지. */
    private fun String.toDisplayDate(): String = runCatching {
        Instant.parse(this)
            .atZone(ZoneId.systemDefault())
            .format(DISPLAY_DATE_FORMATTER)
    }.getOrDefault(this)

    companion object {
        private const val PAGE_SIZE = 20
        private const val INACCESSIBLE_MESSAGE = "이미 만료된 요청이에요"
        private val DISPLAY_DATE_FORMATTER =
            DateTimeFormatter.ofPattern("MM.dd (E) HH:mm", Locale.KOREAN)
    }
}
