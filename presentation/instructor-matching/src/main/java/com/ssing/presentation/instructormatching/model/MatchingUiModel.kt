package com.ssing.presentation.instructormatching.model

import androidx.compose.runtime.Immutable
import com.ssing.core.ui.common.component.Gender
import com.ssing.core.ui.common.component.Participant


// TODO(매칭-API): API 연동 시 MatchingRepository 내 private 메소드로 DTO → UiModel 변환 구현
//  (sport/lessonLevel enum 문자열 → label, durationMinutes → hours 등)


/**
 * 강습 종목 옵션
 **/
internal enum class SportOption(val label: String) {
    SKI("스키"),
    SNOWBOARD("스노보드"),
}

/**
 * 강습 가능 레벨 옵션
 */
internal enum class LevelOption(val label: String) {
    FIRST_TIME("처음타요"),
    BEGINNER("1~5회 타봤어요"),
    INTERMEDIATE("중급자에요"),
    CERTIFIED("자격증이 있어요"),
}

/**
 * 강습 시간 옵션
 */
internal enum class DurationOption(val label: String, val hours: Int) {
    HOUR_2("2시간", 2),
    HOUR_3("3시간", 3),
    HOUR_4("4시간", 4),
}


@Immutable
internal data class MatchingExposureUiState(
    val availableSports: Set<SportOption> = SportOption.entries.toSet(),
    val resortName: String = "",
    val selectedSports: SportOption? = null,
    val selectedLevels: Set<LevelOption> = emptySet(),
    val selectedDurations: Set<DurationOption> = emptySet(),
    val maxHeadcount: Int = 3,
    val maxHeadcountRange: IntRange = 1..5,
    val isNoticeChecked: Boolean = false,
    val isSubmitting: Boolean = false,
) {
    val isStartEnabled: Boolean
        get() = selectedSports != null &&
                selectedLevels.isNotEmpty() &&
                selectedDurations.isNotEmpty() &&
                isNoticeChecked &&
                !isSubmitting

    fun applyProfile(
        availableSports: Set<SportOption>,
        resortName: String,
    ): MatchingExposureUiState = copy(
        availableSports = availableSports,
        resortName = resortName,
        selectedSports = if (availableSports.size == 1) {
            availableSports.first()
        } else {
            selectedSports?.takeIf { it in availableSports }
        },
    )
}

/**
 * 매칭 제안 상태
 */
internal enum class OfferStatusOption {
    OFFERED,
    ACCEPTED,
    REJECTED,
    UNKNOWN,
}

@Immutable
internal data class MatchingOfferUiModel(
    val offerId: Long,
    val groupId: Long,
    val status: OfferStatusOption,
    val expiresAtMillis: Long?,
    val nickname: String = "",
    val teamCount: Int = 0,
    val classDateTime: String = "강습생과 만난 직후 강습 시작",
    val participant: String = "",
    val participants: List<ParticipantUiModel> = emptyList(),
    val isPaid: Boolean = false,
    val price: Int = 0,
    val equipmentStatus: String = "",
    val lesson: LessonSummaryUiModel,
)

/**
 * 강습 조건 요약 — lessonSummary 대응. 표시용 라벨은 매퍼에서 변환해 담는다.
 */
@Immutable
internal data class LessonSummaryUiModel(
    val resortLabel: String,
    val sportLabel: String,
    val levelLabel: String,
    val headcount: Int,
    val durationHours: Int,
)

/**
 * 매칭 대기 화면 카드에 표시할 상태.
 * TODO(매칭-API): Repository 연동 시 실제 데이터로 교체
 */
@Immutable
internal data class MatchingWaitingUiState(
    val nickname: String = "",
    val teamCount: Int = 0,
    val classDateTime: String = "강습생과 만난 직후 강습 시작",
    val participant: String = "",
    val participants: List<ParticipantUiModel> = emptyList(),
    val isPaid: Boolean = false,
    val price: Int = 0,
    val equipmentStatus: String = "",
)

@Immutable
internal data class ParticipantUiModel(
    val age: Int,
    val isMale: Boolean,
)

internal fun ParticipantUiModel.toParticipant() = Participant(
    age = age,
    gender = if (isMale) Gender.MALE else Gender.FEMALE,
)
