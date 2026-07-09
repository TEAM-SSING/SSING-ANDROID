package com.ssing.presentation.consumerpayment

import androidx.compose.runtime.Immutable
import com.ssing.core.ui.common.component.Gender
import com.ssing.core.ui.common.component.Participant
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal interface PaymentContract {

    @Immutable
    data class State(
        val isLoading: Boolean = false,
        val paymentInfo: PaymentInfo,
    )

    sealed interface Effect {
        sealed interface Result : Effect {
            data object PopBackStack : Effect
            data object NavigateToLesson : Effect
            data class ShowToast(val message: String) : Effect
        }
    }

}

@Immutable
data class PaymentInfo(
    val nickname: String = "김00",
    val tags: ImmutableList<String> = persistentListOf("스노보드", "처음타요"),
    val classDateTime: String = "7월 9일 오후 04:40",
    val location: String = "지산리조트",
    val duration: String = "3시간",
    val participants: ImmutableList<Participant> = persistentListOf(
        Participant(11, Gender.MALE),
        Participant(11, Gender.MALE),
        Participant(9, Gender.FEMALE),
    ),
    val equipmentStatus: String = "착용 완료",
    val lessonCost: Int = 60000,
    val resortCost: Int = 20000,
)