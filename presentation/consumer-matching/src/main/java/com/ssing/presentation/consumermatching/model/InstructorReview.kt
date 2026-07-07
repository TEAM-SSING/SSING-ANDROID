package com.ssing.presentation.consumermatching.model

import kotlinx.collections.immutable.ImmutableList

data class InstructorReview(
    val profileImageUrl: String,
    val nickname: String,
    val gender: String,
    val age: Int,
    val rating: Int,
    val tags: ImmutableList<String>,
    val content: String,
    val date: String,
)
