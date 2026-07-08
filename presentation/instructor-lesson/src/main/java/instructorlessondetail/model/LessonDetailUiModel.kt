package instructorlessondetail.model

import androidx.compose.runtime.Immutable
import instructorlessondetail.screen.TeamParticipantsInfo
import kotlinx.collections.immutable.ImmutableList


@Immutable
internal data class LessonDetailBeforeUiState(
    val isInstructorReady: Boolean = false,
    val participantReadyCount: Int = 0,
    val participantTotalCount: Int = 0,
    val tags: ImmutableList<String>,
    val classTitle: String = "",
    val location: String = "",
    val duration: String = "",
    val price: Int = 0,
    val teams: ImmutableList<TeamParticipantsInfo>,
)

@Immutable
internal data class LessonDetailDuringUiState(
    val tags: ImmutableList<String>,
    val classTitle: String = "",
    val location: String = "",
    val duration: String = "",
    val price: Int = 0,
    val teams: ImmutableList<TeamParticipantsInfo>,
)

@Immutable
internal data class LessonDetailAfterUiState(
    val tags: ImmutableList<String>,
    val classTitle: String = "",
    val location: String = "",
    val duration: String = "",
    val price: Int = 0,
    val teams: ImmutableList<TeamParticipantsInfo>,
)

@Immutable
internal data class LessonDetailCanceledUiState(
    val tags: ImmutableList<String>,
    val classTitle: String = "",
    val location: String = "",
    val duration: String = "",
    val price: Int = 0,
    val teams: ImmutableList<TeamParticipantsInfo>,
)
