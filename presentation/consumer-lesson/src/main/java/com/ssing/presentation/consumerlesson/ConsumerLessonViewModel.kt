package com.ssing.presentation.consumerlesson

import com.ssing.core.ui.base.BaseViewModel
import com.ssing.core.ui.common.component.CancelReason
import com.ssing.core.ui.common.component.LessonBannerState
import com.ssing.presentation.consumerlesson.model.CanceledLessonInfoUiModel
import com.ssing.presentation.consumerlesson.model.CompletedLessonInfoUiModel
import com.ssing.presentation.consumerlesson.model.InstructorProfileUiModel
import com.ssing.presentation.consumerlesson.model.LessonInfoUiModel
import com.ssing.presentation.consumerlesson.model.ParticipantTeamUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import javax.inject.Inject

@HiltViewModel
internal class ConsumerLessonViewModel @Inject constructor() :
    BaseViewModel<ConsumerLessonContract.State, ConsumerLessonContract.Effect>(
        ConsumerLessonContract.State()
    ) {

    init {
        // TODO: 서버 연동 후 실제 API 호출로 교체
        loadDummyData()
    }

    private fun loadDummyData() {
        val dummyLessonInfo = LessonInfoUiModel(
            tags = persistentListOf("스노보드", "자격증이 있어요"),
            teamNicknames = persistentListOf("김멍멍", "김야옹"),
            totalCount = 2,
            place = "000 리조트",
            duration = "2시간",
            price = 500000,
        )

        updateState {
            copy(
                lessonBannerState = LessonBannerState.Before(
                    isInstructorReady = true,
                    participantReadyCount = 4,
                    participantTotalCount = 5,
                ),
                lessonInfo = dummyLessonInfo,
                instructorProfile = InstructorProfileUiModel(
                    name = "김어흥 강사",
                    age = 27,
                    gender = "남",
                    level = "grade1",
                    imageUrl = "",
                ),
                participantTeams = persistentListOf(
                    ParticipantTeamUiModel(
                        isReady = true,
                        nickname = "김음메",
                        participants = persistentListOf("38세 남", "12세 여", "9세 남"),
                    ),
                    ParticipantTeamUiModel(
                        isReady = false,
                        nickname = "김끼룩",
                        participants = persistentListOf("38세 남", "12세 여", "9세 남"),
                    ),
                ),
                completedLessonInfo = CompletedLessonInfoUiModel(
                    lessonInfo = dummyLessonInfo,
                    actualTimeRange = "14:00 - 16:00 (2시간)",
                ),
                canceledLessonInfo = CanceledLessonInfoUiModel(
                    lessonInfo = dummyLessonInfo,
                    cancelDateTime = "2026.07.10 14:00",
                    cancelSubject = "강습생",
                    cancelReason = "일정 변경",
                ),
            )
        }
    }

    fun onBack() = sendEffect(ConsumerLessonContract.Effect.NavigationToHome)

    fun onReadyClick() {
        updateState { copy(showReadyAlert = true) }
    }

    fun onReadyDismissed() {
        updateState { copy(showReadyAlert = false) }
    }

    // TODO: 서버 연동 후 수정 - [테스트] participantReadyCount를 로컬에서 직접 증가시킴
    fun onReadyConfirmed() {
        var isAllReady = false

        updateState {
            val before = lessonBannerState as? LessonBannerState.Before ?: return@updateState this
            val updatedBanner = before.copy(participantReadyCount = before.participantReadyCount + 1)
            isAllReady = updatedBanner.totalReadyCount == updatedBanner.totalCount

            copy(
                isReady = true,
                showReadyAlert = false,
                lessonBannerState = updatedBanner,
            )
        }

        if (isAllReady) {
            onLessonStarted(
                remainingTime = "2:59:59",
                elapsedTime = "0분",
            )
        }
    }

    fun onEndLessonClick() {
        updateState { copy(showEndLessonAlert = true) }
    }

    fun onEndLessonDismiss() {
        updateState { copy(showEndLessonAlert = false) }
    }

    fun onEndLessonConfirmed() {
        updateState {
            copy(
                showEndLessonAlert = false,
                lessonBannerState = LessonBannerState.Completed(
                    lessonDate = "2026년 12월 31일",
                ),
            )
        }
    }

    fun onReviewClick() = sendEffect(ConsumerLessonContract.Effect.ShowToast("준비 중인 기능입니다."))

    fun onCancelClick() {
        updateState { copy(showCancelConfirmSheet = true) }
    }

    fun onReasonSelected(reason: CancelReason) {
        updateState { copy(selectedReason = reason) }
    }

    fun onCancelConfirmed(
        etcReason: String? = null,
    ) {
        updateState {
            copy(
                lessonBannerState = LessonBannerState.Canceled,
                showCancelConfirmSheet = false,
                selectedReason = null,
            )
        }
    }

    fun onCancelDismiss() {
        updateState {
            copy(
                showCancelConfirmSheet = false,
                selectedReason = null,
            )
        }
    }

    fun onChatClick() = sendEffect(ConsumerLessonContract.Effect.ShowToast("준비 중인 기능입니다."))

    fun onLessonStarted(remainingTime: String, elapsedTime: String) {
        updateState {
            copy(
                lessonBannerState = LessonBannerState.Ongoing(
                    remainingTime = remainingTime,
                    elapsedTime = elapsedTime,
                ),
                participantTeams = participantTeams
                    .map { it.copy(isReady = false) }
                    .toPersistentList(),
            )
        }
    }

    fun onReportIssueClick() = sendEffect(ConsumerLessonContract.Effect.ShowToast("준비 중인 기능입니다."))

    fun onAdditionalLessonClick() =
        sendEffect(ConsumerLessonContract.Effect.ShowToast("준비 중인 기능입니다."))

    fun onLessonListClick() = sendEffect(ConsumerLessonContract.Effect.ShowToast("준비 중인 기능입니다."))

    fun onHomeClick() = sendEffect(ConsumerLessonContract.Effect.NavigationToHome)
}