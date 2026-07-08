package com.ssing.presentation.instructormatching.instructormatchingdetail

import androidx.compose.runtime.Immutable
import com.ssing.presentation.instructormatching.instructormatchingdetail.screen.TeamParticipantsInfo
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal interface MatchingDetailContract {

    @Immutable
    data class State(
        val isLoading: Boolean = false,
        val isRequesting: Boolean = false,
        val instructorId: Long = 0L,
        val instructorName: String = "",
        val profileImageUrl: String = "",
        val description: String = "",

        val isInstructorReady: Boolean = false,
        val participantReadyCount: Int = 0,
        val participantTotalCount: Int = 0,
        val tags: ImmutableList<String> = persistentListOf(),
        val classTitle: String = "",
        val location: String = "",
        val duration: String = "",
        val price: Int = 0,
        val teams: ImmutableList<TeamParticipantsInfo> = persistentListOf(),
    )

    sealed interface Effect {
        data object NavigateBack : Effect
        data class ShowToast(val message: String) : Effect
        data class NavigateToRequestMatching(val instructorId: Long) : Effect
        data object ShowErrorDialog : Effect

        data object ShowCancelClassDialog : Effect
        data object NavigateToChatRoom : Effect
    }
}