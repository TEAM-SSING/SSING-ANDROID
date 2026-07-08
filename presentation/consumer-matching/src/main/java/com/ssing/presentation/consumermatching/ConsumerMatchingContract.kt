package com.ssing.presentation.consumermatching

import androidx.compose.runtime.Immutable
import com.ssing.presentation.consumermatching.model.InstructorReview
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal interface ConsumerMatchingContract {
    @Immutable
    data class State(
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
    )

    sealed interface Effect {
        sealed interface ResultEffect : Effect {
            data object PopBackStack : ResultEffect
            data object NavigateToHome : ResultEffect
            data object NavigateToPayment : ResultEffect
            data class ShowToast(val message: String) : ResultEffect
        }
    }
}
