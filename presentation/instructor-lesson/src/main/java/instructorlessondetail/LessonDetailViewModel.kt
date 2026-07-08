package instructorlessondetail

import androidx.lifecycle.SavedStateHandle
import com.ssing.core.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class LessonDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<LessonDetailContract.State, LessonDetailContract.Effect>(
    LessonDetailContract.State()
) {

    init {
        val instructorId = savedStateHandle.get<Long>("instructorId") ?: 0L
        updateState { copy(instructorId = instructorId) }
        loadDetail(instructorId)
    }

    private fun loadDetail(instructorId: Long) {
        updateState { copy(isLoading = true) }
    }

    fun onBack() = sendEffect(LessonDetailContract.Effect.NavigateBack)

    fun onRequestMatchingClick() {
        if (uiState.value.isRequesting) return
        updateState { copy(isRequesting = true) }
    }

    fun onBackClick() = sendEffect(LessonDetailContract.Effect.NavigateBack)

    fun onCancelClassClick() {
        sendEffect(LessonDetailContract.Effect.ShowCancelClassDialog)
    }

    fun onChatRoomClick() {
        sendEffect(LessonDetailContract.Effect.NavigateToChatRoom)
    }

    fun onReadyClick() {
        val current = uiState.value.isInstructorReady
        updateState { copy(isInstructorReady = !current) }
    }
}