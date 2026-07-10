package com.ssing.presentation.consumermatching

import androidx.compose.runtime.Immutable
import com.ssing.presentation.consumermatching.model.InstructorReview
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal interface ConsumerMatchingContract {
    @Immutable
    data class State(
        // pending
        val tags: ImmutableList<String> = persistentListOf(),
        val nickname: String = "",
        val teamCount: Int = 0,
        val location: String = "",
        val duration: String = "",
        val price: Int = 0,

        // result
        val showCancelModal: Boolean = false,
        val profileImageUrl: String = "",
        val name: String = "",
        val age: Int = 0,
        val gender: String = "",
        val level: String = "",
        val career: String = "",
        val lessonCount: String = "",
        val rating: String = "",
        val keywords: ImmutableList<String> = persistentListOf(),
        val introduction: String = "",
        val certifications: ImmutableList<String> = persistentListOf(),
        val totalReviewCount: Int = 0,
        val reviews: ImmutableList<InstructorReview> = persistentListOf(),
        val estimatedFee: Int = 0,
        val lessonDuration: String = "",
    ) {
        val detailCardTitle: String =
            "${nickname}님" + if (teamCount > 2) "외 ${teamCount - 1}명" else ""
    }

    sealed interface Effect {
        sealed interface Pending: Effect {
            data object PopBackStack: Pending
            data object NavigateToResult: Pending
            data class ShowToast(val message: String) : Result
        }

        sealed interface Result : Effect {
            data object PopBackStack : Result
            data object NavigateToHome : Result
            data object NavigateToPayment : Result
            data class ShowToast(val message: String) : Result
        }

        sealed interface Failure: Effect {
            data object NavigateToHome : Failure
            data class ShowToast(val message: String) : Failure
        }
    }
}
