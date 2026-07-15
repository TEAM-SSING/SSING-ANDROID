package com.ssing.presentation.consumerhome

import androidx.compose.runtime.Immutable
import com.ssing.core.ui.common.component.HomeLessonCardState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal interface ConsumerHomeContract {
    @Immutable
    data class State(
        val isLoading: Boolean = false,
        val matchingPeopleCount: Long = 0,
        val hasUnreadNotification: Boolean = false,
        val lessonCards: ImmutableList<HomeLessonCardState> = persistentListOf(),
    )

    sealed interface Effect {
        data class ShowToast(val message: String) : Effect
        data object NavigateToMatching : Effect
        data class NavigateToActiveMatching(val matchingRequestId: Long) : Effect
        data class NavigateToLessonDetail(val lessonId: Long) : Effect
    }
}
