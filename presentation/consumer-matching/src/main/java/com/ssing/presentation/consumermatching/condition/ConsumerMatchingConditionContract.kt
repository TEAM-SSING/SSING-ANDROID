package com.ssing.presentation.consumermatching.condition

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Immutable
import com.ssing.presentation.consumermatching.type.ConsumerGender
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

internal const val MAX_CONSUMER_COUNT = 5

internal interface ConsumerMatchingConditionContract {
    @Immutable
    data class State(
        val selectedResort: Resort? = null,
        val selectedSport: Sport? = null,
        val selectedLevel: LessonLevel? = null,
        val selectedDurations: Set<LessonDuration> = setOf(),
        val consumers: PersistentList<ConsumerInfo> = persistentListOf(ConsumerInfo(id = 0)),
        val isConfirmed: Boolean = false,
    ) {
        val isStartMatchingEnabled: Boolean =
            selectedResort != null && selectedSport != null && selectedLevel != null && selectedDurations.isNotEmpty() && isConfirmed &&
                consumers.all { it.gender != null && it.ageState.text.isNotEmpty() }

        val showAddConsumer: Boolean = consumers.size < MAX_CONSUMER_COUNT
    }

    sealed interface Effect {
        data class ShowToast(val message: String) : Effect
        data object NavigateToMatching : Effect
    }
}

@Immutable
internal data class ConsumerInfo(
    val id: Int,
    val ageState: TextFieldState = TextFieldState(),
    val gender: ConsumerGender? = null,
    val isFocused: Boolean = false,
)

internal enum class Resort(
    val api: String,
    val displayName: String,
) {
    HIGH1(
        api = "HIGH1",
        displayName = "하이원리조트",
    ),
    PHOENIX_PARK(
        api = "PHOENIX_PARK",
        displayName = "휘닉스파크",
    ),
    VIVALDI_PARK(
        api = "VIVALDI_PARK",
        displayName = "비발디파크",
    ),
    WELLI_HILLI_PARK(
        api = "WELLI_HILLI_PARK",
        displayName = "웰리힐리파크",
    ),
    ELYSIAN_GANGCHON(
        api = "ELYSIAN_GANGCHON",
        displayName = "엘리시안 강촌",
    ),
    OAK_VALLEY(
        api = "OAK_VALLEY",
        displayName = "오크밸리",
    ),
    ALPENSIA(
        api = "ALPENSIA",
        displayName = "알펜시아",
    ),
    O2_RESORT(
        api = "O2_RESORT",
        displayName = "오투리조트",
    ),
    KONJIAM_RESORT(
        api = "KONJIAM_RESORT",
        displayName = "곤지암리조트",
    ),
    JISAN_FOREST_RESORT(
        api = "JISAN_FOREST_RESORT",
        displayName = "지산포레스트리조트",
    ),
    MUJU_DEOGYUSAN_RESORT(
        api = "MUJU_DEOGYUSAN_RESORT",
        displayName = "무주덕유산리조트",
    );

    companion object {
        val persistentEntries = entries.toPersistentList()
    }
}

internal enum class Sport(
    val api :String,
    val displayName: String,
) {
    SKI(
        api = "SKI",
        displayName = "스키",
    ),
    SNOWBOARD(
        api = "SNOWBOARD",
        displayName = "스노보드",
    )
}

internal enum class LessonLevel(
    val api: String,
    val displayName: String,
) {
    FIRST_TIME(
        api = "FIRST_TIME",
        displayName = "처음 타요",
    ),
    BEGINNER(
        api = "BEGINNER",
        displayName = "1~5회 타봤어요",
    ),
    INTERMEDIATE(
        api = "INTERMEDIATE",
        displayName = "중급자에요",
    ),
    CERTIFIED(
        api = "CERTIFIED",
        displayName = "자격증이 있어요",
    )
}

internal enum class LessonDuration(
    val api: Int,
    val displayName: String,
) {
    TWO_HOUR(
        api = 120,
        displayName = "2시간",
    ),
    THREE_HOUR(
        api = 180,
        displayName = "3시간",
    ),
    FOUR_HOUR(
        api = 240,
        displayName = "4시간",
    )
}
