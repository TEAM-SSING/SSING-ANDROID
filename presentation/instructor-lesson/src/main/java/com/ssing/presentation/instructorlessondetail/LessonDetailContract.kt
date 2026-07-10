package com.ssing.presentation.instructorlessondetail

import androidx.compose.runtime.Immutable
import com.ssing.presentation.instructorlessondetail.model.LessonDetailBeforeUiModel
import com.ssing.presentation.instructorlessondetail.model.LessonDetailCanceledUiModel
import com.ssing.presentation.instructorlessondetail.model.LessonDetailCompletedUiModel
import com.ssing.presentation.instructorlessondetail.model.LessonDetailOngoingUiModel
import com.ssing.presentation.instructorlessondetail.screen.CancelReasonState
import com.ssing.presentation.instructorlessondetail.screen.TeamParticipantsInfo
import kotlinx.collections.immutable.persistentListOf

internal interface LessonDetailContract {

    @Immutable
    data class State(
        val phase: LessonDetailPhase = LessonDetailPhase.LessonDetailBefore(            before = LessonDetailBeforeUiModel(
            isInstructorReady = false,
            participantReadyCount = 2,
            participantTotalCount = 5,
            tags = persistentListOf("스노보드", "자격증이 있어요"),
            classTitle = "김OO님 팀, 홍지민님 팀 총 5명",
            location = "OOO 리조트",
            duration = "0시간",
            price = 0,
            teams = persistentListOf(
                TeamParticipantsInfo(
                    teamNickname = "김OO",
                    teamCount = 0,
                    participants = persistentListOf("38세 남", "12세 여", "9세 남"),
                    price = 0,
                    isReady = true,
                ),
                TeamParticipantsInfo(
                    teamNickname = "김OO",
                    teamCount = 0,
                    participants = persistentListOf("38세 남", "12세 여", "9세 남"),
                    price = 0,
                    isReady = false,
                ),
            ),
        ),
        ),
        val showReadyDialog: Boolean = false,
        val showLessonEndDialog: Boolean = false,
        val cancelReasonState: CancelReasonState = CancelReasonState(),
    )

    sealed interface LessonDetailPhase {
        data object Loading : LessonDetailPhase
        data class LessonDetailBefore(val before: LessonDetailBeforeUiModel) : LessonDetailPhase
        data class LessonDetailOngoing(val ongoing: LessonDetailOngoingUiModel) : LessonDetailPhase
        data class LessonDetailCompleted(val completed: LessonDetailCompletedUiModel) : LessonDetailPhase
        data class LessonDetailCanceled(val cancel: LessonDetailCanceledUiModel) : LessonDetailPhase
    }

    sealed interface LessonDetailDialog {
        data object InstructorReady : LessonDetailDialog
        data object LessonEnd : LessonDetailDialog
    }

    sealed interface Effect {
        data object NavigateBack : Effect
    }
}