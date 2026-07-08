package instructorlessondetail.model

import androidx.compose.runtime.Immutable
import instructorlessondetail.screen.TeamParticipantsInfo
import kotlinx.collections.immutable.ImmutableList


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
)

@Immutable
internal data class LessonDetailDuringUiModel(
    val teams: ImmutableList<TeamParticipantsInfo>,
    val tags: ImmutableList<String>,
    val classTitle: String = "",
    val location: String = "",
    val duration: String = "",
    val price: Int = 0,
)

@Immutable
internal data class LessonDetailAfterUiModel(
    val teams: ImmutableList<TeamParticipantsInfo>,
    val tags: ImmutableList<String>,
    val classTitle: String = "",
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
