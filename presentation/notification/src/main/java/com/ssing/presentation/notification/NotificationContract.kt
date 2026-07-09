package com.ssing.presentation.notification

import androidx.compose.runtime.Immutable
import com.ssing.presentation.notification.model.AlarmUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal interface NotificationContract {

    @Immutable
    data class State(
        val isLoading: Boolean = false,
        val alarms: ImmutableList<AlarmUiModel> = persistentListOf(),
    ) {
        val isEmpty: Boolean
            get() = !isLoading && alarms.isEmpty()
    }

    sealed interface Effect {
        data object NavigateToLessonArrival : Effect

        data object NavigateToMatchingLoading : Effect

        data object NavigateToLessonReady : Effect

        data class ShowToast(val message: String) : Effect
    }
}
