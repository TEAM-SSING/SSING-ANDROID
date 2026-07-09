package com.ssing.presentation.instructorlessondetail.model

import androidx.compose.runtime.Immutable
import com.ssing.core.ui.common.component.TeamNickname
import com.ssing.presentation.instructorlessondetail.screen.TeamParticipantsInfo
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList


@Immutable
internal data class LessonDetailBeforeUiModel(
    val teams: ImmutableList<TeamParticipantsInfo>,
    val tags: ImmutableList<String>,
    val isInstructorReady: Boolean = false,
    val participantReadyCount: Int = 0,
    val participantTotalCount: Int = 0,
    val classTitle: String = "",
    val location: String = "",
    val duration: String = "",
    val price: Int = 0,
) {
    val nicknames =
        teams.map {
            TeamNickname(
                it.teamNickname,
                it.teamCount
            )
        }.toPersistentList()
}

@Immutable
internal data class LessonDetailOngoingUiModel(
    val teams: ImmutableList<TeamParticipantsInfo>,
    val tags: ImmutableList<String>,
    val isContinue: Boolean = false,
    val classTitle: String = "",
    val remainingTime: String = "",
    val elapsedTime: String = "",
    val location: String = "",
    val duration: String = "",
    val price: Int = 0,
)

@Immutable
internal data class LessonDetailCompletedUiModel(
    val teams: ImmutableList<TeamParticipantsInfo>,
    val tags: ImmutableList<String>,
    val classTitle: String = "",
    val lessonDate: String = "",
    val location: String = "",
    val duration: String = "",
    val price: Int = 0,
)

@Immutable
internal data class LessonDetailCanceledUiModel(
    val teams: ImmutableList<TeamParticipantsInfo>,
    val tags: ImmutableList<String>,
    val classTitle: String = "",
    val location: String = "",
    val duration: String = "",
    val price: Int = 0,
)
