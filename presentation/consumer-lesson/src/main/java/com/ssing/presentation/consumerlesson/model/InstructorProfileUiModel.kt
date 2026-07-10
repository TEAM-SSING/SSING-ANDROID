package com.ssing.presentation.consumerlesson.model

import androidx.compose.runtime.Immutable

@Immutable
data class InstructorProfileUiModel(
    val name: String,
    val age: Int,
    val gender: String,
    val level: String,
    val imageUrl: String,
)