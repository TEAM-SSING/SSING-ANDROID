package com.ssing.presentation.instructormatching.instructormatchingdetail

import androidx.compose.runtime.Immutable

internal interface MatchingDetailContract {

    @Immutable
    data class State(
        val isLoading: Boolean = false,
        val isRequesting: Boolean = false,
        val instructorId: Long = 0L,
        val instructorName: String = "",
        val profileImageUrl: String = "",
        val description: String = "",
    )

    sealed interface Effect {
        data object NavigateBack : Effect
        data class ShowToast(val message: String) : Effect
        data class NavigateToRequestMatching(val instructorId: Long) : Effect
        data object ShowErrorDialog : Effect
    }
}