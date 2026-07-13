package com.ssing.presentation.instructorlessondetail.model

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList


@Immutable
internal data class LessonDetailBeforeUiModel(
    val lessonId: Long,
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
    val nicknames: ImmutableList<String> =
        teams.map { it.teamNickname }.toPersistentList()
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
) {
    val nicknames: ImmutableList<String> =
        teams.map { it.teamNickname }.toPersistentList()
}

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

/** 강습생 정보 화면에서 팀 단위로 보여줄 데이터. */
@Immutable
data class TeamParticipantsInfo(
    val teamNickname: String,
    val teamCount: Int,
    val participants: ImmutableList<String>,
    val price: Int,
    val isReady: Boolean? = false,
)
