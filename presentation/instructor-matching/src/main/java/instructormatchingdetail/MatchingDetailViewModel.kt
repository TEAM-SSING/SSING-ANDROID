package com.ssing.presentation.instructormatching.instructormatchingdetail

import androidx.lifecycle.SavedStateHandle
import com.ssing.core.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class MatchingDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<MatchingDetailContract.State, MatchingDetailContract.Effect>(
    MatchingDetailContract.State()
) {

    init {
        val instructorId = savedStateHandle.get<Long>("instructorId") ?: 0L
        updateState { copy(instructorId = instructorId) }
        loadDetail(instructorId)
    }

    private fun loadDetail(instructorId: Long) {
        updateState { copy(isLoading = true) }
    }

    fun onBack() = sendEffect(MatchingDetailContract.Effect.NavigateBack)

    fun onRequestMatchingClick() {
        if (uiState.value.isRequesting) return
        updateState { copy(isRequesting = true) }
    }

    fun onBackClick() = sendEffect(MatchingDetailContract.Effect.NavigateBack)

    fun onCancelClassClick() {
        sendEffect(MatchingDetailContract.Effect.ShowCancelClassDialog)
    }

    fun onChatRoomClick() {
        sendEffect(MatchingDetailContract.Effect.NavigateToChatRoom)
    }

    fun onReadyClick() {
        val current = uiState.value.isInstructorReady
        updateState { copy(isInstructorReady = !current) }
    }
}