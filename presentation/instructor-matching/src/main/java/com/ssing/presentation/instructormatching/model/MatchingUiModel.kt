package com.ssing.presentation.instructormatching.model

import androidx.compose.runtime.Immutable


// TODO(매칭-API): API 연동 시 MatchingRepository 내 private 메소드로 DTO → UiModel 변환 구현
//  (sport/lessonLevel enum 문자열 → label, durationMinutes → hours 등)


/**
 * 강습 종목 옵션
 **/
internal enum class SportOption(val label: String) {
    SKI("스키"),
    SNOWBOARD("보드"),
}

/**
 * 강습 가능 레벨 옵션
 */
internal enum class LevelOption(val label: String) {
    FIRST_TIME("처음 타요"),
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
internal data class ConditionUiState(
    val availableSports: Set<SportOption> = SportOption.entries.toSet(),
    val resortName: String = "",
    val selectedSports: Set<SportOption> = emptySet(),
    val selectedLevels: Set<LevelOption> = emptySet(),
    val selectedDurations: Set<DurationOption> = emptySet(),
    val maxHeadcount: Int = 3,
    val maxHeadcountRange: IntRange = 1..5,
    val isNoticeChecked: Boolean = false,
    val isSubmitting: Boolean = false,
) {
    val isStartEnabled: Boolean
        get() = selectedSports.isNotEmpty() &&
            selectedLevels.isNotEmpty() &&
            selectedDurations.isNotEmpty() &&
            isNoticeChecked &&
            !isSubmitting

    fun applyProfile(
        availableSports: Set<SportOption>,
        resortName: String,
    ): ConditionUiState = copy(
        availableSports = availableSports,
        resortName = resortName,
        selectedSports = if (availableSports.size == 1) availableSports else selectedSports,
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
